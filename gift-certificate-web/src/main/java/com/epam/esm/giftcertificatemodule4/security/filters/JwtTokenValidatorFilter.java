package com.epam.esm.giftcertificatemodule4.security.filters;

import com.epam.esm.giftcertificatemodule4.security.constants.SecurityConstants;
import com.epam.esm.giftcertificatemodule4.services.impl.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JwtTokenValidatorFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String jwtBearer = request.getHeader(SecurityConstants.JWT_HEADER);

        if (jwtBearer != null) {
            String jwt = jwtBearer.replace(SecurityConstants.JWT_BEARER, "");
            try {
                SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();
                Long userId = Long.parseLong(String.valueOf(claims.get("sub")));
                String username = String.valueOf(claims.get("username"));
                String authorities = (String) claims.get("authorities");
                UserDetails userDetails = new UserDetailsImpl(userId, username);
                Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }catch (Exception e) {
                throw new BadCredentialsException("Invalid Token received!");
            }
        }
        chain.doFilter(request, response);
    }

    @Override protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().contains("api/auth"); }
}
