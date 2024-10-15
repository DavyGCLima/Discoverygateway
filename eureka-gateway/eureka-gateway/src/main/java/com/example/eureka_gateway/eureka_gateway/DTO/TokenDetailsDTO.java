package com.example.eureka_gateway.eureka_gateway.DTO;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDetailsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> roles;
    private String login;
    private Long countResponsibleEntities;
    private String token;
}