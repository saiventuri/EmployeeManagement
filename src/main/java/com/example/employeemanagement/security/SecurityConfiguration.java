package com.example.employeemanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_URLS = {
            "/v3/api-docs",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**",
            "/user/save",
            "/user/login",
            "/console/**",
            "/h2-console/**"
    };
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private SecurityFilter securityFilter;

    /**
     * Gets the {@link AuthenticationManager} to use. The default strategy is if
     * {@link #configure(AuthenticationManagerBuilder)} method is overridden to use the
     * {@link AuthenticationManagerBuilder} that was passed in. Otherwise, autowire the
     * {@link AuthenticationManager} by type.
     *
     * @return the {@link AuthenticationManager} to use
     * @throws Exception
     */
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(PUBLIC_URLS).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .headers().frameOptions().disable();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(11);
    }
}
