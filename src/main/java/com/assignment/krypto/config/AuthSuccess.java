package com.assignment.krypto.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * This is our custom authentication success handler
 * 
 * @author Megha
 *
 */
@Component
public class AuthSuccess extends SimpleUrlAuthenticationSuccessHandler {

	/**
	 * This callback method is called when authentication is success
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,DELETE,PUT");
	}

	
}