package com.sa1zer.simplebanking.api;

import com.sa1zer.simplebanking.payload.exception.BaseException;
import com.sa1zer.simplebanking.payload.response.BaseMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = {BindException.class, })
    public ResponseEntity<?> handleBindingResult(BindException exception) {
        BindingResult result = exception.getBindingResult();
        if(result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            if(!CollectionUtils.isEmpty(result.getFieldErrors())) {
                for(FieldError e : result.getFieldErrors()) {
                    errors.put(e.getField(), e.getDefaultMessage());
                }
            }

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<?> handleBaseErrors(BaseException ex) {
        return ResponseEntity.badRequest().body(new BaseMessageResponse(ex.getMessage(), 400));
    }
}
