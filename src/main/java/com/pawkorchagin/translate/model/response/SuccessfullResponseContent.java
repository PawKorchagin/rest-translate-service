package com.pawkorchagin.translate.model.response;

import lombok.RequiredArgsConstructor;

/**
 * SuccessfullResponseContent
 */
@RequiredArgsConstructor
public class SuccessfullResponseContent extends Response {
    private final String text;
}
