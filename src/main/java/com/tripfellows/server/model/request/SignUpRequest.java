package com.tripfellows.server.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class SignUpRequest {
    private String username;
    private String password;
    private String name;
    private String phoneNumber;
}
