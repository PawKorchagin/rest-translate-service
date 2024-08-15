package com.pawkorchagin.translate.service.impl;

import java.io.IOException;
import java.sql.SQLException;

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

/**
 * Service implementation that performs the job of handling translation requests 
 * in a non-parallel manner. This service interacts with an API client to process 
 * the request and a repository to log user sessions.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ServiceNonParallel implements IService {

    @Autowired
    private final ApiClient client;

    @Autowired 
    private final UserSessionsRepository rep;

    /**
     * Processes a translation request and sava the user's session info to database. This method 
     * makes a synchronous call to an external translation API and records the user's 
     * session in the database.
     *
     * @param request the request object containing the text to be translated and the target language
     * @param ip the IP address of the user making the request
     * @return a {@link ResponseEntity} containing the response from the translation API
     * @throws IllegalStateException if the {@link ApiClient} or {@link UserSessionsRepository} beans are not properly injected
     */
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
        } catch (ClassNotFoundException | IOException | SQLException e) {
            log.error(e.getMessage());
        }

        return response;
    }
}
