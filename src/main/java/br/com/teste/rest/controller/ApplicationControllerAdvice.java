package br.com.teste.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.teste.exception.RegraNegocioException;
import br.com.teste.rest.ApiErrors;

@RestControllerAdvice
public class ApplicationControllerAdvice {
    
    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleCadastroException(RegraNegocioException ex) {
        return new ApiErrors(ex.getMessage());
    }

}
