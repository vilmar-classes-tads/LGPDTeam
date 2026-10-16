package controller;

import dto.ErroResponse;
import exceptions.ValidacaoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroControllerAdvice {

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<ErroResponse> tratarValidacao(ValidacaoException exception) {
        return ResponseEntity.badRequest()
                .body(new ErroResponse(exception.getMessage(), exception.getCampos()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponse> tratarCorpoInvalido(HttpMessageNotReadableException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponse("Os dados enviados são inválidos."));
    }
}
