package com.pawkorchagin.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.color.LogPainter;
import com.pawkorchagin.client.ApiClient;
import com.pawkorchagin.service.IService;
import com.pawkorchagin.translate.model.request.YandexApiRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ServiceNonParallel implements IService {
    private final ApiClient client;

    @Override
    public ResponseEntity<?> doJob(YandexApiRequest request) {
        log.info("Starting non-parallel service job");

        log.debug("Request Content: {}", request.toString());

        if (client == null) {
            log.error(LogPainter.purple("ApiClient is not initialized"));
            throw new IllegalStateException("ApiClient bean is not injected properly");
        }
        
        ResponseEntity<?> response = client.makeRequest(request);
        return response;
    }
}
