package com.assignment.krypto.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * This is custom authentication failure handler
 * 
 * @author Mayank
 *
 */
@Component
public class AuthFailure extends SimpleUrlAuthenticationFailureHandler {
    @Value("${server.url}")
    private String serverUrl;

    private static Logger logger = LoggerFactory.getLogger(AuthFailure.class);

    /**
     * This callback method is called when authentication fail
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.createObjectNode();

        if (exception instanceof BadCredentialsException) {
            ((ObjectNode) rootNode).put("msg", "Username or password is incorrect");
            response.getWriter().write(rootNode.toString());
        } else {
            ((ObjectNode) rootNode).put("msg", request.getAttribute("msg").toString());
            response.getWriter().write(rootNode.toString());
        }
        logger.debug("User authentication failed");
    }

}
