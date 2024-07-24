package ma.petpulse.petpulsecore.service.services.implementations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import ma.petpulse.petpulsecore.config.JwtConfig;
import ma.petpulse.petpulsecore.dao.entities.User;
import ma.petpulse.petpulsecore.enumerations.Role;
import ma.petpulse.petpulsecore.service.services.interfaces.IJwtService;


@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService implements IJwtService {
    private final JwtConfig jwtConfig;

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Long getUserId(String token) {
        return Long.parseLong(extractAllClaims(token).get("id").toString());
    }

    @Override
    public Role getUserRole(String token) {
        return Role.valueOf(extractAllClaims(token).get("role").toString());
    }

    @Override
    public String generateToken(User user) {
        return generateToken(Map.of("role", user.getRole(), "id", user.getId(),
                "email", user.getEmail(),"name", user.getFirstName()), user);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){

        return Jwts
                .builder()
                .addClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())) // when this claim was created
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getAccessTokenExpiredAfter()))
                .signWith(jwtConfig.getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, String subject, Key secret, long expiredAfter) {
        return Jwts
                .builder()
                .addClaims(extraClaims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredAfter))
                .signWith(secret, jwtConfig.getAlgorithm())
                .compact();
    }

    @Override
    public String generateAccessToken(User user) {
        return generateToken(
                Map.of("role", user.getRole(),
                        "id", user.getId(),
                        "email", user.getEmail(),
                        "fullName", user.getFirstName() + " " + user.getLastName()
                ),
                user.getEmail(),
                jwtConfig.getSignInKey(),
                jwtConfig.getAccessTokenExpiredAfter()
        );
    }

    @Override
    public User getUserFromToken(String jwt) {
        User user = new User();
        Claims claims = extractAllClaims(jwt);
        user.setId(Long.parseLong(claims.get("id").toString()));
        user.setFirstName(claims.get("name").toString());
        user.setRole(Role.valueOf(claims.get("role").toString()));
        return user;
    }

    @Override
    public User getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken){
            return (User) authentication.getPrincipal();
        }
        return null;
    }

    // Helper method to get access token claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtConfig.getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // extract a specific claim from the token
    public <T> T extractClaim(String token, Function<Claims,T> ClaimsResolver){
        final Claims claims = extractAllClaims(token);
        return ClaimsResolver.apply(claims);
    }

    // validate token
    @Override
    public Boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public boolean isTokenExpired(String token) {
        assert extractExpiration(token) != null;
        return extractExpiration(token).before(new Date());
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
