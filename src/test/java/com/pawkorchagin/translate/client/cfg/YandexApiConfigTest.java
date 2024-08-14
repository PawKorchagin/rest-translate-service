package com.pawkorchagin.translate.client.cfg;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class YandexApiConfigTest {

    @Autowired
    private YandexApiConfig yandexApiConfig;

    @Test
    public void testGetIamToken() {
        assertNotNull(yandexApiConfig.getIamToken(), "IAM Token should not be null");
        assertTrue(yandexApiConfig.getIamToken().startsWith("t1."), "IAM Token should start with 't1.'");
    }

    @Test
    public void testGetUrl() {
        assertNotNull(yandexApiConfig.getUrl(), "URL should not be null");
        assertTrue(yandexApiConfig.getUrl().startsWith("https://"), "URL should start with 'https://'");
    }

    @Test
    public void testGetFolderId() {
        assertNotNull(yandexApiConfig.getFolderId(), "Folder ID should not be null");
        assertTrue(yandexApiConfig.getFolderId().length() > 0, "Folder ID should not be empty");
    }
}
