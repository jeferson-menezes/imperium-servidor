package com.zionflame.imperiumserver.config.exeption;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;


public class ForbiddenExceptionHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {

        HttpStatus httpStatus = HttpStatus.FORBIDDEN; // 403        
        response.setStatus(httpStatus.value());

        ForbiddenExceptionDetails data = ForbiddenExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN.value())
                .title(HttpStatus.FORBIDDEN.name())
                .developerMessage(
                        exception.getClass().getName())
                .message(
                        exception.getMessage())
                .build();
        response.getOutputStream().println(new ObjectMapper().writeValueAsString(data));
    }

}

