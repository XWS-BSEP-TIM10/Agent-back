package com.agent.security.util;

import com.agent.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

// Utility klasa za rad sa JSON Web Tokenima
@Component
public class TokenUtils {

    // Izdavac tokena
    @Value("spring-security-example")
    private static final String APP_NAME = "spring-security-example";

    // Tajna koju samo backend aplikacija treba da zna kako bi mogla da generise i proveri JWT https://jwt.io/
    @Value("somesecret")
    public static final String SECRET = "somesecret";

    // Period vazenja tokena - 30 minuta
    @Value("1800000")
    private static final int EXPIRES_IN = 1800000;

    @Value("3600000")
    private static final int REFRESH_EXPIRES_IN = 3600000;

    // Naziv headera kroz koji ce se prosledjivati JWT u komunikaciji server-klijent
    @Value("Authorization")
    private static final String AUTH_HEADER = "Authorization";


    private static final String AUDIENCE_WEB = "web";

    // Algoritam za potpisivanje JWT
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;


    // ============= Funkcije za generisanje JWT tokena =============

    /**
     * Funkcija za generisanje JWT tokena.
     *
     * @return JWT token
     */
    public String generateToken(String role, String username, String id, Boolean isRefreshToken, String companyId) {
        return Jwts.builder()
                .setIssuer(APP_NAME)
                .setSubject(username)
                .claim("role", role)
                .claim("companyId", companyId)
                .claim("userId", id)
                .setAudience(generateAudience())
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate(isRefreshToken))
                .signWith(SIGNATURE_ALGORITHM, SECRET).compact();

    }

    /**
     * Funkcija za utvrđivanje tipa uređaja za koji se JWT kreira.
     *
     * @return Tip uređaja.
     */
    private String generateAudience() {

        //	Moze se iskoristiti org.springframework.mobile.device.Device objekat za odredjivanje tipa uredjaja sa kojeg je zahtev stigao.
        //	https://spring.io/projects/spring-mobile

        return AUDIENCE_WEB;
    }

    /**
     * Funkcija generiše datum do kog je JWT token validan.
     *
     * @return Datum do kojeg je JWT validan.
     */
    private Date generateExpirationDate(Boolean isRefreshToken) {
        Date date;
        if (isRefreshToken.equals(true)) {
            date = new Date(new Date().getTime() + REFRESH_EXPIRES_IN);
        } else {
            date = new Date(new Date().getTime() + EXPIRES_IN);
        }
        return date;
    }

    // =================================================================

    // ============= Funkcije za citanje informacija iz JWT tokena =============

    /**
     * Funkcija za preuzimanje JWT tokena iz zahteva.
     *
     * @param request HTTP zahtev koji klijent šalje.
     * @return JWT token ili null ukoliko se token ne nalazi u odgovarajućem zaglavlju HTTP zahteva.
     */
    public String getToken(HttpServletRequest request) {
        String authHeader = getAuthHeaderFromHeader(request);

        // JWT se prosledjuje kroz header 'Authorization' u formatu:
        // Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // preuzimamo samo token (vrednost tokena je nakon "Bearer " prefiksa)
        }

        return null;
    }

    /**
     * Funkcija za preuzimanje vlasnika tokena (korisničko ime).
     *
     * @param token JWT token.
     * @return Korisničko ime iz tokena ili null ukoliko ne postoji.
     */
    public String getUsernameFromToken(String token) {
        String username;

        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            if (claims == null || claims.isEmpty())
                return null;
            username = claims.getSubject();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            username = null;
        }

        return username;
    }

    public String getUserIdFromToken(String token) {
        String userId;

        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            if (claims == null || claims.isEmpty())
                return null;
            userId = (String) claims.get("userId");
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            userId = null;
        }

        return userId;

    }

    /**
     * Funkcija za preuzimanje datuma kreiranja tokena.
     *
     * @param token JWT token.
     * @return Datum kada je token kreiran.
     */
    public Date getIssuedAtDateFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            if (claims == null || claims.isEmpty())
                return null;
            issueAt = claims.getIssuedAt();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    /**
     * Funkcija za preuzimanje informacije o uređaju iz tokena.
     *
     * @param token JWT token.
     * @return Tip uredjaja.
     */
    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            if (claims == null || claims.isEmpty())
                return null;
            audience = claims.getAudience();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    /**
     * Funkcija za preuzimanje datuma do kada token važi.
     *
     * @param token JWT token.
     * @return Datum do kojeg token važi.
     */
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            if (claims == null || claims.isEmpty())
                return null;
            expiration = claims.getExpiration();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            expiration = null;
        }

        return expiration;
    }

    /**
     * Funkcija za čitanje svih podataka iz JWT tokena
     *
     * @param token JWT token.
     * @return Podaci iz tokena.
     */
    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            claims = null;
        }

        // Preuzimanje proizvoljnih podataka je moguce pozivom funkcije claims.get(key)

        return claims;
    }

    // =================================================================

    // ============= Funkcije za validaciju JWT tokena =============

    /**
     * Funkcija za validaciju JWT tokena.
     *
     * @param token       JWT token.
     * @param userDetails Informacije o korisniku koji je vlasnik JWT tokena.
     * @return Informacija da li je token validan ili ne.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        User user = (User) userDetails;
        final String username = getUsernameFromToken(token);

        // Token je validan kada:
        return (username != null // korisnicko ime nije null
                && username.equals(user.getUsername())); // korisnicko ime iz tokena se podudara sa korisnickom imenom koje pise u bazi
        // nakon kreiranja tokena korisnik nije menjao svoju lozinku
    }


    // =================================================================

    /**
     * Funkcija za preuzimanje perioda važenja tokena.
     *
     * @return Period važenja tokena.
     */
    public int getExpiredIn() {
        return EXPIRES_IN;
    }

    /**
     * Funkcija za preuzimanje sadržaja AUTH_HEADER-a iz zahteva.
     *
     * @param request HTTP zahtev.
     * @return Sadrzaj iz AUTH_HEADER-a.
     */
    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(AUTH_HEADER);
    }

}
