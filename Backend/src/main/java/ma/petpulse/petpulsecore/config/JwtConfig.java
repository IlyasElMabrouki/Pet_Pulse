package ma.petpulse.petpulsecore.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.security.Key;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    // store this secret in a secure place
    private String accessTokenSecret = "f41205cfefdd6717494bf9764416e64935d830d1cd0330202943805b3bfde998";
    private long accessTokenExpiredAfter = 1000*60*24; // 24 hours
    private SignatureAlgorithm algorithm = HS256;

    public void setAlgorithm(String algorithm) {
        this.algorithm = SignatureAlgorithm.valueOf(algorithm);
    }

    public Key getSignInKey() {
        // Convert the secret to a byte array
        byte[] keyBytes = Decoders.BASE64.decode(accessTokenSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
