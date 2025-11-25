package com.project.serverapp.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtHelper {

  @Value("${jwt.secret}")
  private String SECRET;

  /**
   * - 1000 ms = 1 detik
   * - 60 detik = 1 menit
   * - 60 menit = 1 jam
   * - 24 jam = 1 hari
   */
  private final long EXPIRATION = 1000 * 60 * 60 * 24; // 24 jam

  // method to generate signing key
  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(SECRET.getBytes());
  }

  // method to generate token
  public String generateToken(String username) {
    return Jwts
      .builder()
      .subject(username) // set subject as username - payload utama
      .issuedAt(new Date()) // set issued at sebagai waktu sekarang
      .expiration(new Date(System.currentTimeMillis() + EXPIRATION)) // set expiration time
      .signWith(getSigningKey()) // sign the token with the signing key
      .compact(); // build the token

      // contoh hasil token:
      // xxxxx.yyyyy.zzzzz
      // eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTY4ODUwMjI5NSwiZXhwIjoxNjg4NTg4Njk1fQ.Xx7pX3Z5bX1Z5YzVjY2JlY2RmZjA0ZGI3ZjEwY2E3YmE
  }

  // method to extract username from token
  public String extractUsername(String token) {
    Claims claims = Jwts
      .parser()
      .verifyWith(getSigningKey()) // verify with signing key
      .build() 
      .parseSignedClaims(token) // parse the token
      .getPayload(); // get the payload (claims)

    return claims.getSubject(); // return the subject (username)
  }

  // method to validate token
  public boolean isTokenValid(String token) {
    try {
      Jwts.parser().verifyWith(getSigningKey()).build().parse(token); // NEW API 
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
