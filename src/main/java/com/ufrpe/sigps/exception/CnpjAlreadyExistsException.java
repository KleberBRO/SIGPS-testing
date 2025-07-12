// src/main/java/com/ufrpe/sigps/exception/CnpjAlreadyExistsException.java
package com.ufrpe.sigps.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando se tenta criar uma Startup com um CNPJ que já existe.
 * A anotação @ResponseStatus faz com que o Spring retorne automaticamente o status HTTP 409 Conflict
 * sempre que esta exceção não for capturada e tratada por um ControllerAdvice.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class CnpjAlreadyExistsException extends RuntimeException {

    /**
     * Construtor que aceita uma mensagem de erro.
     * @param message A mensagem detalhando a causa do erro.
     */
    public CnpjAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Construtor que aceita uma mensagem de erro e a causa original.
     * @param message A mensagem detalhando a causa do erro.
     * @param cause A exceção original que causou este erro.
     */
    public CnpjAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}