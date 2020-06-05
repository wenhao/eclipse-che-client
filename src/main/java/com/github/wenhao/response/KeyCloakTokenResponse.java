package com.github.wenhao.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeyCloakTokenResponse {
    @JsonProperty("access_token")
    private String token;
}
