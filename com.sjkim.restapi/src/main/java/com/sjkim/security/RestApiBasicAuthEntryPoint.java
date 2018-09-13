package com.sjkim.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

public class RestApiBasicAuthEntryPoint extends BasicAuthenticationEntryPoint {
	
	@Value("${auth.realm}")
	private String realm;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 : " + authException.getMessage());
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		setRealmName(realm);
		super.afterPropertiesSet();
	}
}