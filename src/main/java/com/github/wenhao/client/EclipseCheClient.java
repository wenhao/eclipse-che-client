package com.github.wenhao.client;

import com.github.wenhao.configutation.FormPostConfiguration;
import com.github.wenhao.response.KeyCloakTokenResponse;
import feign.Body;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(
        name = "EclipseChe",
        url = "${application.eclipse.che.url}",
        configuration = FormPostConfiguration.class
)
public interface EclipseCheClient {

    @PostMapping(path = "/auth/realms/che/protocol/openid-connect/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KeyCloakTokenResponse getToken(Map<String, ?> formParams);

    @PostMapping(path = "/api/workspace/devfile?start-after-create=false", consumes = "text/yaml")
    @Body(value = "${devFile}")
    void create(@RequestHeader("Authorization") String token, String devFile);
}
