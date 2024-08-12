package com.pawkorchagin.client.authorization;


import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationClient {

    public HttpHeaders createAuthorizationHeader() throws UnsupportedOperationException {
        HttpHeaders headers = new HttpHeaders();

        // headers.setBearerAuth(TOKEN);
        // headers.set("Content-Type", "application/json");

        return headers;
    }
}
