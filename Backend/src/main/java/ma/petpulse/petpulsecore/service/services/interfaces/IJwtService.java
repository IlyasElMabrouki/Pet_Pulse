package ma.petpulse.petpulsecore.service.services.interfaces;

import ma.petpulse.petpulsecore.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetails;
import ma.petpulse.petpulsecore.dao.entities.User;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public interface IJwtService {
    String extractUsername(String token);

    Long getUserId(String token);

    Role getUserRole(String token);

    String generateToken(User User);

    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    String generateToken(Map<String, Object> extraClaims, String subject, Key secret, long expiredAfter);

    String generateAccessToken(User User);

    User getUserFromToken(String jwt);

    // Helper methode to generate access and refresh tokens
    // to avoid redundancy in code
    //    AuthenticationResponse generateTokens(User user);

    User getAuthenticatedUser();

    // validate token
    Boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);

    Date extractExpiration(String token);
}
