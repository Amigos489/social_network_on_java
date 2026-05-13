package social_network.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    private final Integer validityTokenInMinutes;

    private final SecretKey secretKey;

    public JwtService(@Value("${jwt.validity-token-in-minutes}") Integer validityTokenInMinutes,
                      @Value("${jwt.secret}") String secret) {
        this.validityTokenInMinutes = validityTokenInMinutes;
        this.secretKey = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret)
        );
    }

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("iss", "social_network");
        claims.put("login", userDetails.getUsername());

        Instant now = Instant.now();

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(TimeUnit.MINUTES.toMillis(validityTokenInMinutes))))
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getSubject();
    }

    private Claims getClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        try {
            String username = extractUsername(jwt);
            Claims claims = getClaims(jwt);
            return username.equals(userDetails.getUsername())
                    && claims.getExpiration().after(Date.from(Instant.now()));
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }
}
