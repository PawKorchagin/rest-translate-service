package com.pawkorchagin.translate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.pawkorchagin.client.ApiClient;
import com.pawkorchagin.client.cfg.YandexApiConfig;
import com.pawkorchagin.service.IService;
import com.pawkorchagin.service.impl.ServiceNonParallel;
import com.pawkorchagin.translate.model.request.YandexApiRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller
 */
@RestController
@RequestMapping
@ControllerAdvice
@Slf4j
public class Controller {
    private final IService service = new ServiceNonParallel(new ApiClient(new YandexApiConfig(), new RestTemplate()));

    @PostMapping("/translate")
    public ResponseEntity<?> translate(@RequestBody YandexApiRequest request) {
        return service.doJob(request);
    }
    @PostMapping("/echo")
    public ResponseEntity<?> echoJson(@RequestBody Object requestBody) {
        log.info("start echo");
        return new ResponseEntity<>(requestBody, HttpStatus.OK);
    }
}
