package com.bikram.appliedproject.configuration;

import com.bikram.appliedproject.security.JwtAuthenticationEntry;
import com.bikram.appliedproject.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class config extends WebSecurityConfigurerAdapter {

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntry jwtAuthenticatioEntry;


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().
                authorizeRequests()
                .antMatchers("/token/*").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/listUser").permitAll()
                .antMatchers("/typeApi/*").permitAll()
                .antMatchers("/productUserApi/*").permitAll()
                .antMatchers("/productApi/*").permitAll()
                .antMatchers(HttpMethod.GET, "/homeApi/*").permitAll()
                .antMatchers("/productApi/paging").permitAll()
                .antMatchers("/homeApi/product-info/*").permitAll()
                .antMatchers("/cartApi/listCart").permitAll()
                .antMatchers("/cartApi/*").permitAll()
                .antMatchers("/cartApi/listCart").permitAll()
                .antMatchers("/stripeApi/*").permitAll()
                .antMatchers("/shippingApi/*").permitAll()
                .antMatchers("/confirm").permitAll()
                .antMatchers("/homeApi/demo").permitAll()
                .antMatchers("/paypalApi/complete/payment").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticatioEntry).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

}
