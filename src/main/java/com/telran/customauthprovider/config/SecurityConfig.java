package com.telran.customauthprovider.config;

import com.telran.customauthprovider.business.CustomDetailsService;
import com.telran.customauthprovider.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity()
public class SecurityConfig {

    @Autowired
    UserRepository userRepository;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService())
                .passwordEncoder(encoder());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder encoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Configuration
    static class AppSecurityConfig extends WebSecurityConfigurerAdapter{
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.GET,"/account/all").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE,"/account/{username}").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/account").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .httpBasic();
        }
    }
}
