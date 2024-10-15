package com.example.eureka_gateway.eureka_gateway.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {

    @NotNull
    private String userName;

    @NotNull
    private String password;

}
