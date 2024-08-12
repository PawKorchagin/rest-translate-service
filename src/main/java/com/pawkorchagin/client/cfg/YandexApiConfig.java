package com.pawkorchagin.client.cfg;

import org.springframework.context.annotation.Configuration;

import com.color.LogPainter;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * YandexApiConfig
 */
// @RequiredArgsConstructor
// @Getter
/**
 * YandexApiConfig
 */
@Configuration
@Getter
@Slf4j
public class YandexApiConfig {
    // @Value("${yandex.translate.endpoint.url}")
    private String URL = "https://translate.api.cloud.yandex.net/translate/v2/translate";
    // @Value("${yandex.cloud.folder.id}")
    private String FOLDER_ID  = "b1gtnk8rf36ddjmv1r";
    // @Value("${yandex.cloud.iam.token}")
    private String TOKEN = "t1.9euelZrOkJ6RzseUxsacmsyRkpPJxu3rnpWayc6JlZvJm5CWm5uVyp3Gis_l8_d-YR1K-e8iSU58_d3z9z4QG0r57yJJTnz9zef1656Vms2Qi82djpmOmcyXmcyRicbL7_zF656Vms2Qi82djpmOmcyXmcyRicbL.PYTaCCo9z0IoX7EbQ9frVmb3lPAie3x0vwfqCJrcOkB6etDk8DDI8Ef3bgbVRZ16KUfeFuAtXuOYvLMPldRO";

    @Override
    public String toString() {
        return URL + " " + FOLDER_ID + " " + TOKEN + null;
    }

    @PostConstruct
    public void init() {
        log.debug("post construct check");
        log.debug(LogPainter.purple("FolderId: " + FOLDER_ID));
        log.debug(LogPainter.purple("Token: " + TOKEN));
        log.debug(LogPainter.purple("Url: " + URL));
    }
}
