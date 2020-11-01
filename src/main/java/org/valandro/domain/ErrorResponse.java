package org.valandro.domain;

import lombok.Builder;

@Builder
public class ErrorResponse {
    private final Integer code;
    private final String exceptionType;
    private final String message;
}
