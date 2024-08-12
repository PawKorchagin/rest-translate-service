package com.pawkorchagin.client;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.pawkorchagin.client.cfg.YandexApiConfig;
import com.pawkorchagin.translate.model.request.YandexApiRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ApiClient {
    private final YandexApiConfig cfg;
    private final RestTemplate restTemplate; // Inject this via constructor

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

            ResponseEntity<Map> response = restTemplate.exchange(cfg.getURL(), HttpMethod.POST, entity, Map.class);

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

        headers.setBearerAuth(cfg.getTOKEN());
        headers.set("Content-Type", "application/json");

        if (cfg.getFOLDER_ID() == null || request.texts() == null || request.sourceLanguageCode() == null || request.targetLanguageCode() == null) {
            log.error("map takes nulls");
        }

        if (cfg.getFOLDER_ID() == null) {
            log.error("null folder id");
        }
        
        if (request.sourceLanguageCode() == null) {
            log.error("null source lang");
        }

        if (request.targetLanguageCode() == null) {
            log.error("null target lang");
        }

        if (request.texts() == null) {
            log.error("null texts");
        }

        log.debug("Config content: {}", cfg.toString());

        Map<String, Object> requestBody = Map.of(
            "folderId", cfg.getFOLDER_ID(),
            "texts", request.texts(),
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
