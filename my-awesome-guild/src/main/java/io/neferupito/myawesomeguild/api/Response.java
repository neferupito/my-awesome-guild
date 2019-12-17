package io.neferupito.myawesomeguild.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class Response<T> {

    @JsonIgnore
    private transient HttpStatus errorHttpStatus;
    private boolean isError;
    private Integer externalHttpStatus;
    private String externalHttpMessage;
    private T content;

}