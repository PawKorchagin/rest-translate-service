package com.pawkorchagin.translate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pawkorchagin.translate.client.ApiClient;
import com.pawkorchagin.translate.color.MessagePainter;
import com.pawkorchagin.translate.model.request.YandexApiRequest;
import com.pawkorchagin.translate.service.IService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ServiceNonParallel implements IService {
    @Autowired
    private final ApiClient client;

    @Override
    public ResponseEntity<?> doJob(YandexApiRequest request) {
        log.info("Starting non-parallel service job");

        log.debug("Request Content: {}", request.toString());

        if (client == null) {
            log.error(MessagePainter.purple("ApiClient is not initialized"));
            throw new IllegalStateException("ApiClient bean is not injected properly");
        }
        
        ResponseEntity<?> response = client.makeRequest(request);
        return response;
    }
}
