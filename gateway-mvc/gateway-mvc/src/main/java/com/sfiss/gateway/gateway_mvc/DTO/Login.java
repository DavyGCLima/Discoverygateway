package com.sfiss.gateway.gateway_mvc.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {

    @NotNull
    private String username;

    @NotNull
    private String password;

    private Boolean rememberMe = false;
}
