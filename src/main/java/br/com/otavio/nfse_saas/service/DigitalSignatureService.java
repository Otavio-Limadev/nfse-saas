package br.com.otavio.nfse_saas.service;

import br.com.otavio.nfse_saas.DigitalCertificate;
import org.apache.xml.security.Init;
import org.apache.xml.security.algorithms.MessageDigestAlgorithm;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.bouncycastle.jce.provider.BouncyCastleProvider; // Import necessário
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security; // Import necessário
import java.security.cert.X509Certificate;

@Service
public class DigitalSignatureService {

    public String signXml(String xml, DigitalCertificate certificate) throws Exception {
        Init.init();

        Security.addProvider(new BouncyCastleProvider());

        String senha = certificate.getPassword();

        KeyStore ks = KeyStore.getInstance("PKCS12", "BC");
        System.out.println("Tamanho do arquivo do certificado: " + certificate.getFileData().length + " bytes");
        ks.load(new ByteArrayInputStream(certificate.getFileData()), senha.toCharArray());

        String alias = ks.aliases().nextElement();
        PrivateKey privateKey = (PrivateKey) ks.getKey(alias, senha.toCharArray());
        X509Certificate x509Certificate = (X509Certificate) ks.getCertificate(alias);

        return applyXmlDsig(xml, privateKey, x509Certificate);
    }

    private String applyXmlDsig(String xml, PrivateKey privateKey, X509Certificate certificate) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        Document doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes()));

        XMLSignature sig = new XMLSignature(doc, "", XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA256);
        doc.getDocumentElement().appendChild(sig.getElement());

        Transforms transforms = new Transforms(doc);
        transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);
        transforms.addTransform(Transforms.TRANSFORM_C14N_EXCL_OMIT_COMMENTS);

        sig.addDocument("", transforms, MessageDigestAlgorithm.ALGO_ID_DIGEST_SHA256);

        sig.sign(privateKey);
        sig.addKeyInfo(certificate.getPublicKey());
        sig.addKeyInfo(certificate);

        StringWriter sw = new StringWriter();
        TransformerFactory.newInstance().newTransformer().transform(new DOMSource(doc), new StreamResult(sw));
        return sw.toString();
    }
}