package com.pawkorchagin.translate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pawkorchagin.translate.model.request.YandexApiRequest;
import com.pawkorchagin.translate.service.IService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * This controller handles translation and echo requests. It provides endpoints 
 * for translating text using an external API and for echoing back received JSON data.
 */
@RestController
@RequestMapping
@ControllerAdvice
@Slf4j
public class Controller {

    @Autowired
    private IService service;

    /**
     * Endpoint to translate text. This method receives a translation request, logs 
     * the client's IP address, and forwards the request to the service layer.
     *
     * @param request        the translation request containing the text and languages
     * @param servletRequest the HTTP servlet request, used to extract the client's IP address
     * @return a {@link ResponseEntity} containing the translation response or an error status
     */
    @PostMapping("/translate")
    public ResponseEntity<?> translate(@RequestBody YandexApiRequest request, HttpServletRequest servletRequest) {
        var ip = servletRequest.getRemoteAddr();
        log.debug("ip: {}", ip);
        return service.doJob(request, ip);
    }

    /**
     * Endpoint to echo back the received JSON request. This method simply logs the
     * start of the echo process and returns the received request body.
     *
     * @param requestBody the JSON object received in the request body
     * @return a {@link ResponseEntity} containing the received JSON object with an HTTP 200 status
     */
    @PostMapping("/echo")
    public ResponseEntity<?> echoJson(@RequestBody Object requestBody) {
        log.info("start echo");
        return new ResponseEntity<>(requestBody, HttpStatus.OK);
    }
}
