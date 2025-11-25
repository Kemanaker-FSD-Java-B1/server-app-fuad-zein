package com.project.serverapp.config;

import com.project.serverapp.helper.JwtHelper;
import com.project.serverapp.service.AppUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor
// filter untuk setiap request, untuk memeriksa apakah ada token JWT di header Authorization
public class JwtAuthenticationFilterConfig extends OncePerRequestFilter {

  private JwtHelper jwtHelper;
  private AppUserDetailService userDetailService;

  @Override
  protected void doFilterInternal(
    @NonNull HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    // mengambil header Authorization - Authorization: Bearer <token>
    String header = request.getHeader("Authorization");

    // jika header tidak ada atau tidak diawali dengan "Bearer " - lanjutkan ke filter berikutnya sebagai anonymous
    if (header == null || !header.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    // mengambil token dari header
    String token = header.substring(7);
    // mengambil username dari token
    String username = jwtHelper.extractUsername(token);

    // jika username ada dan belum ada authentication di context
    if (
      username != null &&
      SecurityContextHolder.getContext().getAuthentication() == null
    ) {
      // load user details dari database
      UserDetails userDetails = userDetailService.loadUserByUsername(username);

      // validasi token
      if (jwtHelper.isTokenValid(token)) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.getAuthorities()
        );

        // set details dari request
        authToken.setDetails(
          // mengambil info tambahan dari request seperti IP, session ID, user-agent
          new WebAuthenticationDetailsSource().buildDetails(request)
        );

        // set authentication di context
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }

    // lanjutkan ke filter berikutnya
    filterChain.doFilter(request, response);
  }
}
