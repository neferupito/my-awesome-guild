package io.neferupito.myawesomeguild.api.domain.api;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class Response<T> {

    private HttpStatus errorHttpStatus;
    private boolean isError;
    private T content;

}