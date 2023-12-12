package com.its.service.exception;


import com.its.service.utils.ResponseBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.BadRequestException;
import org.json.simple.JSONObject;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import static com.its.service.constant.ValidatorConstants.ALREADY_EXIST;
import static org.springframework.http.ResponseEntity.badRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<JSONObject> handleNotFoundException(ResourceNotFoundException ex, WebRequest request) {

        ResponseBuilder rb = ResponseBuilder.error(null, ex.getMessage());
        rb.setData(new Object());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(rb.getJson());
    }

    public final ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse response = new ErrorResponse(
                new Date(),
                ex.getMessage(),
                "",
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> errors = ex.getAllErrors().stream()
                .filter(error -> !StringUtils.isEmpty(error.getDefaultMessage()))
                .collect(Collectors.toMap(error -> ((FieldError) error).getField(), DefaultMessageSourceResolvable::getDefaultMessage,
                        (existing, replacement) -> existing + " & " + replacement));

        return badRequest().body(ResponseBuilder.error(errors, "Bad Request").getJson());
    }

    @ExceptionHandler(CustomMessagePresentException.class)
    public ResponseEntity<JSONObject> handleCustomMessagePresentExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ResponseBuilder.error((ex.getMessage())).getJson(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DataExistsException.class})
    public ResponseEntity<JSONObject> dataExistsException(Exception ex, WebRequest request) {
        String message = ex.getMessage().equals("") ? ALREADY_EXIST : ex.getMessage();
        return new ResponseEntity<>(ResponseBuilder.error((message)).getJson(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<JSONObject> handleBadRequestExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ResponseBuilder.error((ex.getMessage())).getJson(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AlreadyExistsException.class})
    public ResponseEntity<JSONObject> handleAlreadyExistsExceptions(Exception ex, WebRequest request) {
        String message = ex.getMessage().equals("") ? ALREADY_EXIST : ex.getMessage();
        return new ResponseEntity<>(ResponseBuilder.error((ex.getMessage())).getJson(), HttpStatus.BAD_REQUEST);
    }
}

