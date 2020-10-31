package org.valandro.exception;

import lombok.Getter;
import javax.ws.rs.core.Response.Status;
import java.io.Serializable;
import java.util.Map;

@Getter
public class HttpErrorException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -7056744141915529104L;

    private final String message;
    private final Status httpStatus;
    private final Map<String, String> details;

    public
    HttpErrorException(String message, Status status) {
        super(message);
        this.message = message;
        this.httpStatus = status;
        this.details = null;
    }

    public HttpErrorException(String message, Status status, Map<String, String> details) {
        super(message);
        this.message = message;
        this.httpStatus = status;
        this.details = details;
    }
}