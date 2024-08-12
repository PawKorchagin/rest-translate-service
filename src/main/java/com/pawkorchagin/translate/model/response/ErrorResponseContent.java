package com.pawkorchagin.translate.model.response;

import lombok.RequiredArgsConstructor;

/**
 * ErrorResponseContent
 */
@RequiredArgsConstructor
public class ErrorResponseContent extends Response {
    private final String message;
}
