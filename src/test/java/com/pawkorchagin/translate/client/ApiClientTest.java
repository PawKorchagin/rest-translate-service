package com.pawkorchagin.translate.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pawkorchagin.translate.model.request.YandexApiRequest;

@SpringBootTest
public class ApiClientTest {

    private final ApiClient client;

    @Autowired
    public ApiClientTest(ApiClient client) {
        this.client = client;
    }

    @Test
    void testMakeRequest() {
        assertNotNull(client);
        YandexApiRequest request = new YandexApiRequest(new String[]{"text"}, "en", "ru");
        var response = client.makeRequest(request);
        assertEquals("{translations=[{text=текст}]}", response.getBody());
    }
}
