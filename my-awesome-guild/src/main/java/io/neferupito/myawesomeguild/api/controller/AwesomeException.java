package io.neferupito.myawesomeguild.api.controller;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class AwesomeException extends Exception {

    private HttpStatus status;
    private String errorMessage;

    public AwesomeException(HttpStatus status, String errorMessage) {
        super();
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public ResponseEntity<Object> buildResponseEntity() {
        return ResponseEntity
                .status(getStatus()).body(getErrorMessage());
    }

}
