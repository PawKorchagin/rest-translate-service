package com.pawkorchagin.translate.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.pawkorchagin.translate.client.cfg.YandexApiConfig;
import com.pawkorchagin.translate.model.request.YandexApiRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApiClient {
    @Autowired
    private final YandexApiConfig cfg;
    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<?> makeRequest(YandexApiRequest request) {
        try {
            if (request == null) {
                log.error("Received null request");
                return ResponseEntity.badRequest().body("Invalid request");
            }

            if (restTemplate == null) {
                log.error("null restTemplate provided");
            }

            HttpEntity<Map<String, Object>> entity = prepareRequestEntity(request);

            if (entity == null) {
                log.error("null http entity");
            }

            ResponseEntity<Map> response = restTemplate.exchange(cfg.getUrl(), HttpMethod.POST, entity, Map.class);

            return handleResponse(response);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("API request failed with status: {} and body: {}", e.getStatusCode(), e.getResponseBodyAsString());
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (NullPointerException  e) {
            log.error("Error occurred in request service api client:\nMessage: {}\nError: {}\nStack trace: {}", e.getMessage(), e.toString(), e.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("api client request error");
        }  catch (Exception e) {
            log.error("An unexpected error occurred {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    private HttpEntity<Map<String, Object>> prepareRequestEntity(YandexApiRequest request) {
        HttpHeaders headers = new HttpHeaders();

        if (cfg == null) {
            log.error("cfg is null on apiclient.java 62");
        }

        if (request == null) {
            log.error("request is null on apiclient.java 66");
        }

        headers.setBearerAuth(cfg.getIamToken());
        headers.set("Content-Type", "application/json");

        log.debug("Config content: {}", cfg.toString());

        String[] content = request.texts();

        if (content == null) {
            log.error("request text is null");
        }

        Map<String, Object> requestBody = Map.of(
            "folderId", cfg.getFolderId(),
            "texts", content,
            "sourceLanguageCode", request.sourceLanguageCode(),
            "targetLanguageCode", request.targetLanguageCode()
        );

        if (requestBody == null) {
            log.error("null request body");
        }

        return new HttpEntity<>(requestBody, headers);
    }

    private ResponseEntity<?> handleResponse(ResponseEntity<Map> response) throws NullPointerException {
        if (response == null) {
            throw new NullPointerException("null response error");
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(response.getBody().toString());
        } else {
            log.error("API responded with non-success status: {}", response.getStatusCode());
            return ResponseEntity.status(response.getStatusCode()).body("API request failed");
        }
    }
}
