package com.example.employeemanagement.security;

import com.example.employeemanagement.security.jwt.JwtUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, @NotNull FilterChain filterChain) throws ServletException, IOException {
        //1. Read token from authorization header
        String token = httpServletRequest.getHeader("Authorization");

        if (token != null) {
            // do validation
            String userName = jwtUtil.getTokenUserName(token);

            // username should not be empty
            // context authentication must be null
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

                // Validate the token
                boolean isValid = jwtUtil.validateToken(token, userDetails.getUsername());

                if (isValid) {
                    // Generate the token
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName,
                            userDetails.getPassword(),
                            userDetails.getAuthorities());
                    // Linking the token with current request
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    // Connecting to security context holder
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                }
            }
        }

        // else go to next filter/servlet
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
