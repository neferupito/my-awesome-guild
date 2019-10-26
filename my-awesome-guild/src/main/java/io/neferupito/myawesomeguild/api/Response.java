package io.neferupito.myawesomeguild.api;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class Response<T> {

    private transient HttpStatus errorHttpStatus;
    private boolean isError;
    private T content;

}