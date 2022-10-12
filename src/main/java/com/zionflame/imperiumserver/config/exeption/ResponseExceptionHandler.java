package com.zionflame.imperiumserver.config.exeption;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.zionflame.imperiumserver.controller.dto.ValidatorDetailsDto;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BadRequestExceptionDetails> handleViolationException(ConstraintViolationException ex) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title(HttpStatus.BAD_REQUEST.name())
                        .developerMessage(ex.getClass().getName())
                        .message(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handleBadRequest(BadRequestException ex) {

        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title(HttpStatus.BAD_REQUEST.name())
                        .developerMessage(ex.getClass().getName())
                        .message(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BadRequestExceptionDetails> handleNotFound(NotFoundException ex) {

        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .title(HttpStatus.NOT_FOUND.name())
                        .developerMessage(ex.getClass().getName())
                        .message(ex.getMessage())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<UnauthorizedExceptionDetails> handleUnauthorized(UnauthorizedException ex) {
        return new ResponseEntity<>(
                UnauthorizedExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .title(HttpStatus.UNAUTHORIZED.name())
                        .developerMessage(ex.getClass().getName())
                        .message(ex.getMessage())
                        .build(),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ClienteInvalidoException.class)
    public ResponseEntity<UnauthorizedExceptionDetails> handleUnauthorized(ClienteInvalidoException ex) {

        return new ResponseEntity<>(
                UnauthorizedExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .title(HttpStatus.UNAUTHORIZED.name())
                        .developerMessage(ex.getClass().getName())
                        .message(ex.getMessage())
                        .build(),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<PayloadTooLargeExceptionDetails> handlePayloadTooLarge(MaxUploadSizeExceededException ex) {

        return new ResponseEntity<>(
                PayloadTooLargeExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.PAYLOAD_TOO_LARGE.value())
                        .title(HttpStatus.PAYLOAD_TOO_LARGE.name())
                        .developerMessage(ex.getCause().getMessage())
                        .message(ex.getMessage()).build(),
                HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ExceptionDetails> handleInternalServerError(InternalServerErrorException ex){
         ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .title(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .developerMessage(ex.getClass().getName())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .title(status.name())
                .developerMessage(ex.getClass().getName())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(exceptionDetails, headers, status);
    }
    
    

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ValidatorDetailsDto> fields = new ArrayList<>();

        fieldErrors.forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            fields.add(new ValidatorDetailsDto(e.getField(), mensagem));
        });

        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, Invalid Fields")
                        .message("Campos inválidos")
                        .developerMessage(ex.getClass().getName())
                        .fields(fields)
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

}
