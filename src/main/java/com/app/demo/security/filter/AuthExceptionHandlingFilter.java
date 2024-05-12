package com.app.demo.security.filter;

import java.io.IOException;

import com.app.demo.apiPayload.BaseResponse;
import com.app.demo.apiPayload.code.status.ErrorStatus;
import com.app.demo.apiPayload.exception.AuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthExceptionHandlingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AuthException e) {
            response.setContentType("application/json; charset=UTF-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            ErrorStatus code = (ErrorStatus) e.getCode();

            BaseResponse<Object> errorResponse =
                    BaseResponse.onFailure(code.getCode(), code.getMessage(), null);

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), errorResponse);
        }
    }
}