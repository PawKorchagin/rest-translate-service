package com.pawkorchagin.translate.client.cfg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * YandexApiConfig
 */
@Component
@Getter
@Slf4j
@RequiredArgsConstructor
public class YandexApiConfig {
    @Value("${yandex.translate.url}")
    private String url;
    @Value("${folder.id}")
    private String folderId;
    @Value("${iam.token}")
    private String iamToken ;
    @Override
    public String toString() {
        return url + " " + folderId + " " + iamToken;
    }
}
