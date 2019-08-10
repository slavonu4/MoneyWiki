package org.money.wiki.presentation.controller;

import org.money.wiki.presentation.dto.ErrorDTO;
import org.money.wiki.presentation.exception.BadMnemonicsException;
import org.money.wiki.presentation.exception.RemoteServiceError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GeneralController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralController.class);

    @ExceptionHandler(BadMnemonicsException.class)
    public ResponseEntity<ErrorDTO> handleBadMnemonicsException(HttpServletRequest req, BadMnemonicsException ex){
        LOGGER.error("Attempt to get info for invalid mnemonics {} at {}", ex.getMnemonics(), req.getRequestURI());

        String message = String.format("Mnemonics %s are not supported", ex.getMnemonics());

        return ResponseEntity.badRequest()
                .body(new ErrorDTO(message));
    }

    @ExceptionHandler(RemoteServiceError.class)
    public ResponseEntity<ErrorDTO> handleRemoteServiceException(HttpServletRequest req, RemoteServiceError ex){
        LOGGER.error("Remote service error on call to {}", req.getRequestURI());

        String message = "Sorry! We can`t retrieve exchange rate info for your request";

        return new ResponseEntity<>(new ErrorDTO(message), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
