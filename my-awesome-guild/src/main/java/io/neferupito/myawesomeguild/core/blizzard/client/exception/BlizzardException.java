package io.neferupito.myawesomeguild.core.blizzard.client.exception;

import lombok.Getter;

@Getter
public class BlizzardException extends Exception {

    private Integer status;
    private String errorMessage;

    public BlizzardException(Integer status, String errorMessage) {
        super();
        this.status = status;
        this.errorMessage = errorMessage;
    }
}
