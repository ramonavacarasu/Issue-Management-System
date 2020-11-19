package ims.security.boundary;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TokenIssuer {

    //Set token validity to 60 minutes from issued time
    public static final long EXPIRY_MINS = 60L;

    //Issue token for given username
    public String issueToken(String username) {

        // Expiration requires a Date, so use below to get the Date instance
        LocalDateTime expiryPeriod = LocalDateTime.now().plusMinutes(EXPIRY_MINS);
        Date expirationDateTime = Date.from(expiryPeriod.atZone(ZoneId.systemDefault()).toInstant());

        //Use "secret" to generate a JWT

        Key key = new SecretKeySpec("secret".getBytes(), "DES");

        String compactJws = Jwts.builder()
                .setSubject(username) // The subject will be the username itself
                .claim("scope", "admin approves") // Can be used to set roles
                .signWith(SignatureAlgorithm.HS256, key) // The alg used
                .setIssuedAt(new Date())
                .setExpiration(expirationDateTime) // Expire token after this
                .compact();

        return compactJws;
    }
}
