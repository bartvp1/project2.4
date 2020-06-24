package MeetUpAPI.service;

import java.util.Base64;
import java.util.Date;
import java.util.HashSet;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import MeetUpAPI.errorHandling.CustomException;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

  HashSet<String> blackListedTokens = new HashSet<>();

  @Value("${security.jwt.token.secret-key}")
  private String secretKey;

  @Value("${security.jwt.token.expire-length}")
  private long validityInMilliseconds;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createToken(String username) {
    Claims claims = Jwts.claims().setSubject(username);
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);
    String token = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    return token;
  }

  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    try{
      if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
        if(validateToken(bearerToken.substring(7))) return bearerToken.substring(7);
      } else {
        throw new CustomException("No Authorization header present",HttpStatus.UNAUTHORIZED);
      }
    } catch (JwtException e){
      throw new CustomException("Invalid / Expired JWT token",HttpStatus.UNAUTHORIZED);
    }
    throw new CustomException("Unprocessable request",HttpStatus.UNPROCESSABLE_ENTITY);
  }

  private boolean validateToken(String token) throws CustomException {
    try {
      if(!blackListedTokens.contains(token)){
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return true;
      } else throw new CustomException("Blacklisted JWT token, log back in", HttpStatus.UNAUTHORIZED);
    } catch (JwtException e){
      throw new CustomException("Expired / Invalid JWT token", HttpStatus.UNAUTHORIZED);
    }
  }

  public boolean authenticatedRequest(HttpServletRequest httpServletRequest) {
    String token = resolveToken(httpServletRequest);

    return (token != null && validateToken(token));
  }

  public void logout(String token) {
    this.blackListedTokens.add(token);
  }
}
