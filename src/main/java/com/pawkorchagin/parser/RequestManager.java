package com.pawkorchagin.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawkorchagin.translate.model.request.YandexApiRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RequestManager {
    private final ObjectMapper mapper;

    public String convertYandexApiRequestToJson(YandexApiRequest request) throws JsonProcessingException {
        return mapper.writeValueAsString(request);
    }

    public String getMessageFromJson(String json) throws JsonProcessingException, JsonMappingException {
        var jsonNode = mapper.readTree(json);
        return jsonNode.get("message").asText();
    }
}
