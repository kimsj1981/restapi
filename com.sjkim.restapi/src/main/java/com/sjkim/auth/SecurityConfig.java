package com.sjkim.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${auth.key.id}")
	private String apiKeyId;

	@Value("${auth.key.password}")
	private String apiKeyPass;

	@Value("${auth.role}")
	private String role;

	@Value("${auth.realm}")
	private String realm;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/api/**").hasRole(role).and().httpBasic()
				.realmName(realm).authenticationEntryPoint(getBasicAuthEntryPoint());
	}

	@Bean
	public RestApiBasicAuthenticationEntryPoint getBasicAuthEntryPoint() {
		return new RestApiBasicAuthenticationEntryPoint();
	}

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser(apiKeyId).password("{noop}" + apiKeyPass).roles(role);
	}
}