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
 * Controller
 */
@RestController
@RequestMapping
@ControllerAdvice
@Slf4j
public class Controller {
    @Autowired
    private IService service;

    @PostMapping("/translate")
    public ResponseEntity<?> translate(@RequestBody YandexApiRequest request, HttpServletRequest servletRequest) {
        log.debug(servletRequest.getRemoteAddr());
        return service.doJob(request);
    }

    @PostMapping("/echo")
    public ResponseEntity<?> echoJson(@RequestBody Object requestBody) {
        log.info("start echo");
        return new ResponseEntity<>(requestBody, HttpStatus.OK);
    }
}
