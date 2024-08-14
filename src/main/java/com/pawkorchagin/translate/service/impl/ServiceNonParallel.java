package com.pawkorchagin.translate.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pawkorchagin.translate.client.ApiClient;
import com.pawkorchagin.translate.color.MessagePainter;
import com.pawkorchagin.translate.model.request.YandexApiRequest;
import com.pawkorchagin.translate.repository.UserSessionsRepository;
import com.pawkorchagin.translate.service.IService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ServiceNonParallel implements IService {
    @Autowired
    private final ApiClient client;
    @Autowired 
    private final UserSessionsRepository rep;

    @Override
    public ResponseEntity<?> doJob(YandexApiRequest request, String ip) {
        log.info("Starting non-parallel service job");

        log.debug("Request Content: {}", request.toString());

        if (client == null) {
            log.error(MessagePainter.purple("ApiClient is not initialized"));
            throw new IllegalStateException("ApiClient bean is not injected properly");
        }

        if (rep == null) {
            log.error(MessagePainter.purple("Repository is not initialized"));
            throw new IllegalStateException("UserSessionsRepository bean is not injected properly");
        }
        
        ResponseEntity<?> response = client.makeRequest(request);

        try {
            rep.addUserSession(ip);
        } catch (IOException e) {
        }

        return response;
    }
}
