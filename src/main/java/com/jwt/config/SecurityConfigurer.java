package com.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jwt.filter.JwtRequestFilter;
import com.jwt.services.MyUserDetailService;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	@Autowired
	private MyUserDetailService myUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService);
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().authorizeRequests().antMatchers("/authenticate").permitAll().anyRequest()
				.authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.cors().and().csrf().disable();
//	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
