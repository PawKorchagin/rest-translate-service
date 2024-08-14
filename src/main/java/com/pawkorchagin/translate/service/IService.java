package com.pawkorchagin.translate.service;

import org.springframework.http.ResponseEntity;

import com.pawkorchagin.translate.model.request.YandexApiRequest;

/**
 * IService
 */
public interface IService {
    public ResponseEntity<?> doJob(YandexApiRequest request);
}