package org.valandro.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
public class ExampleResponse implements Serializable {

    private static final long serialVersionUID = -4058873444592061777L;

    private final String text;
}
