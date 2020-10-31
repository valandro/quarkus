package org.valandro.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccessLogRequest {

    public AccessLogRequest() {
        super();
    }

    public AccessLogRequest(String firstName, String lastName, String email, String gender, String ipAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.ipAddress = ipAddress;
    }

    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String ipAddress;
}
