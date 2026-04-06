package personalbudget.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
	
	 private final String SECRET = "mysecretkeymysecretkeymysecretkey123"; // uzun olmalıdır

	    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

	    // TOKEN CREATE
	    public String generateToken(String email) {
	        return Jwts.builder()
	                .setSubject(email)
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 saat
	                .signWith(key, SignatureAlgorithm.HS256)
	                .compact();
	    }

	    // TOKEN READ
	    public String extractEmail(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(key)
	                .build()
	                .parseClaimsJws(token)
	                .getBody()
	                .getSubject();
	    }

	    // VALIDATE
	    public boolean validateToken(String token) {
	        try {
	            extractEmail(token);
	            return true;
	        } catch (Exception e) {
	            return false;
	        }
	    }
	
	

}
