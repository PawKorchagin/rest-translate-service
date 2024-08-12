package com.pawkorchagin.translate.model.request;

/**
 * YandexApiRequest
 */
public record YandexApiRequest(String[] texts, String sourceLanguageCode, String targetLanguageCode) {
}