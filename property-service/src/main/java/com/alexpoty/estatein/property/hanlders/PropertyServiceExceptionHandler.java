package com.alexpoty.estatein.property.hanlders;

import com.alexpoty.estatein.property.dto.ApiResponse;
import com.alexpoty.estatein.property.dto.ErrorDto;
import com.alexpoty.estatein.property.exception.PropertyAlreadyExistsException;
import com.alexpoty.estatein.property.exception.PropertyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class PropertyServiceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleMethodArgumentException(MethodArgumentNotValidException exception) {
        ApiResponse<?> serviceResponse = new ApiResponse<>();
        List<ErrorDto> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    ErrorDto errorDto = new ErrorDto(error.getField(), error.getDefaultMessage());
                    errors.add(errorDto);
                });
        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(errors);
        return serviceResponse;
    }

    @ExceptionHandler(PropertyNotFoundException.class)
    public ApiResponse<?> handlePropertyNotFoundException(PropertyNotFoundException exception) {
        ApiResponse<?> serviceResponse = new ApiResponse<>();
        serviceResponse.setStatus("Failed");
        serviceResponse.setErrors(Collections.singletonList(new ErrorDto("", exception.getMessage())));
        return serviceResponse;
    }

    @ExceptionHandler(PropertyAlreadyExistsException.class)
    public ApiResponse<?> handlePropertyAlreadyExistsException(PropertyAlreadyExistsException exception) {
        ApiResponse<?> serviceResponse = new ApiResponse<>();
        serviceResponse.setStatus("Failed");
        serviceResponse.setErrors(Collections.singletonList(new ErrorDto("", exception.getMessage())));
        return serviceResponse;
    }
}
