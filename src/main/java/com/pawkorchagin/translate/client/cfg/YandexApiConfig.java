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
    private String url;// = "https://translate.api.cloud.yandex.net/translate/v2/translate";
    @Value("${folder.id}")
    private String folderId;// = "b1gtnk8rf36ddjmv1rc6";
    @Value("${iam.token}")
    private String iamToken ;//= "t1.9euelZrHkM2SjcbIyZaXj5uRmJKSxu3rnpWayc6JlZvJm5CWm5uVyp3Gis_l8_dlSxNK-e93Th4l_t3z9yV6EEr573dOHiX-zef1656Vmp7Jl8qRiZSQmZbGloycjpWM7_zF656Vmp7Jl8qRiZSQmZbGloycjpWM.YFbE4cQ_PEr1pMscZu1rkct71R9vCF74-bJb4dWC0_sXRGnRpnWZByLuC1Xpgs8cVOYr_UNKjcoBN3f1BW61Dw";
    @Override
    public String toString() {
        return url + " " + folderId + " " + iamToken;
    }
}
