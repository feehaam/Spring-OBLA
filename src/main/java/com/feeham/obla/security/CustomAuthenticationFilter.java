package com.feeham.obla.security;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feeham.obla.config.SpringApplicationContext;
import com.feeham.obla.model.auths.LoginRequestModel;
import com.feeham.obla.model.auths.LoginResponseModel;
import com.feeham.obla.model.userdto.UserReadDTO;
import com.feeham.obla.service.interfaces.UserService;
import com.feeham.obla.utilities.constants.TokenConstants;
import com.feeham.obla.utilities.token.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/user/login");
    }

    private final Map<String, Integer> attemptCount = new HashMap<String, Integer>();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginRequestModel credentials = null;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);
        } catch (IOException e) {
            writeResponse(response, "Exception while reading credentials", 400);
        }
        attemptCount.put(credentials.getEmail(), attemptCount.getOrDefault(credentials.getEmail(), 0) + 1);
        return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getEmail(),credentials.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        User user = (User) authResult.getPrincipal();
        String username = user.getUsername();
        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
        UserReadDTO userReadDto = userService.readByEmail(username);

        if (attemptCount.get(userReadDto.getEmail()) > TokenConstants.MAX_LOGIN_ATTEMPTS_LIMIT) {
            restrictedResponse(response);
            return;
        } else {
            attemptCount.put(userReadDto.getEmail(), 0);
        }

        String userRole = userReadDto.getRole().getRoleName();
        List<String> userRoles = new ArrayList<>();
        userRoles.add("ROLE_"+userRole);
        String accessToken = JWTUtils.generateToken(username, userRoles);

        LoginResponseModel responseBody = new LoginResponseModel(userReadDto.getEmail(), accessToken, userRoles);
        writeResponse(response, responseBody, 200);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "Username or email wrong. Max failed attempt: "+TokenConstants.MAX_LOGIN_ATTEMPTS_LIMIT);
        writeResponse(response, errorResponse, 400);
    }

    private void restrictedResponse(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("Restricted", "Your account has been locked for "+TokenConstants.MAX_LOGIN_ATTEMPTS_LIMIT +" failed attempts.");
        writeResponse(response, errorResponse, 403);
    }

    private void writeResponse(HttpServletResponse response, Object object, int statusCode){
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try{
            new ObjectMapper().writeValue(response.getWriter(), object);
        }
        catch (IOException ioe){
            log.error("Failed to write in response.", ioe);
        }
    }
}