package com.realestate.invest.Config.JWT;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.realestate.invest.Config.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * @The {@code JwtHelper} class provides utility methods for working with JSON Web Tokens (JWTs).
 * @It can generate JWTs, retrieve information from JWTs, and validate JWTs.
 * 
 * @author Abhishek Srivastav
 */
@Component
public class JwtHelper 
{

    private String SECRET_KEY = "abhisheksrivastasoftwareenginnerFROMLUCKNOWKMCLTHESPYDERWAVESoriginfrompokharakazidumariyaganjsiddharthanagar";


    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * @Retrieve the username from a JWT token.
     *
     * @param token The JWT token.
     * @return The username extracted from the token.
     */
    public String getUsernameFromToken(String token) 
    {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * @Retrieve the expiration date from a JWT token.
     *
     * @param token The JWT token.
     * @return The expiration date of the token.
     */
    public Date getExpirationDateFromToken(String token) 
    {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * @Retrieve a specific claim from a JWT token.
     *
     * @param token The JWT token.
     * @param claimsResolver A function to retrieve the desired claim from the token's claims.
     * @param <T> The type of the claim.
     * @return The claim value.
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) 
    {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) 
    {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token).getBody();
    }

    /**
     * @Check if a JWT token has expired.
     *
     * @param token The JWT token.
     * @return True if the token has expired, false otherwise.
     */
    //check if the token has expired
    private Boolean isTokenExpired(String token) 
    {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) 
    {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) 
    {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) 
    {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * @Generate a JWT token for a user.
     *
     * @param userDetails The UserDetails of the user.
     * @return The generated JWT token.
     */  
    public String generateToken(UserDetails userDetails) 
    {
        Map<String, Object> claims = new HashMap<>();claims.put("key",SECRET_KEY);
        claims.put("password", userDetails.getPassword());
        claims.put("role", getRolesAsString(userDetails.getAuthorities()));
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * @Generate a JWT token.
     *
     * @param claims The claims to include in the token.
     * @param subject The subject of the token (usually the username).
     * @return The generated JWT token.
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) 
    {
        UserDetails user = userDetailsService.loadUserByUsername(subject);
        claims.put("role", user.getAuthorities());
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        
        long currentTimeMillis = System.currentTimeMillis();
        long durationMillis = (95 * 60 * 60 * 1000) + (59 * 60 * 1000) + (59 * 1000);
        long expirationTimeMillis = currentTimeMillis + durationMillis;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
        String formattedDate = sdf.format(new Date(expirationTimeMillis));
        System.out.println("Expiration Time: " + formattedDate);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(expirationTimeMillis))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * @Validate a JWT token.
     *
     * @param token The JWT token to validate.
     * @param userDetails The UserDetails of the user.
     * @return True if the token is valid for the user, false otherwise.
     */
    public Boolean validateToken(String token, UserDetails userDetails) 
    {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateRefreshToken(String refreshToken) 
    {
        try 
        {
            final String username = extractUsername(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            validateToken(refreshToken, userDetails);
            return generateToken(userDetails);
        } 
        catch (ExpiredJwtException e) 
        {
            e.getLocalizedMessage();
            return "Refresh token has expired";
        } 
        catch (Exception e) 
        {
            return "Error refreshing token";
        }
    } 


    private String getRolesAsString(Collection<? extends GrantedAuthority> authorities) 
    {
        StringBuilder rolesBuilder = new StringBuilder();
        for (GrantedAuthority authority : authorities) 
        {
            rolesBuilder.append(authority.getAuthority()).append(","); // Append role
        }
        if (rolesBuilder.length() > 0) 
        {
            rolesBuilder.deleteCharAt(rolesBuilder.length() - 1);
        }
        return rolesBuilder.toString();
    }



// ==============================================Custom Secret Key Configuration ==========================================================
    // private String secret = generateSecretKey();

    // private String generateSecretKey() {
    //     SecureRandom random = new SecureRandom();
    //     byte[] keyBytes = new byte[64]; // You can adjust the size of the key as needed

    //     random.nextBytes(keyBytes);

    //     return Base64.getEncoder().encodeToString(keyBytes); Base64isfrom Util
    // }
}
