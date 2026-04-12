package app.oworld.auth.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class KeyLoader {
    @Value("${spring.security.private-key}")
    private Resource privateKeyResource;

    @Value("${spring.security.public-key}")
    private Resource publicKeyResource;

    public PrivateKey loadPrivateKey() throws Exception{
        String key = readKey(privateKeyResource)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decode = Base64.getDecoder().decode(key);

        return KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(decode));
    }


    public PublicKey loadPublicKey() throws Exception {
        String key = readKey(publicKeyResource)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(key);

        return KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(decoded));
    }

    private String readKey(Resource resource) throws Exception{
        try(InputStream is = resource.getInputStream()){
            return new String(is.readAllBytes());
        }
    }
}
