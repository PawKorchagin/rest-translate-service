package com.pawkorchagin.translate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pawkorchagin.translate.model.request.YandexApiRequest;
import com.pawkorchagin.translate.service.IService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * This controller handles translation and echo requests. It provides endpoints 
 * for translating text using an external API and for echoing back received JSON data.
 */
@org.springframework.stereotype.Controller
@RequestMapping
@ControllerAdvice
@Slf4j
public class Controller {

    @Autowired
    private IService service;

    /**
     * Displays the translation form to the user. 
     * This method serves the initial page where users can input text for translation.
     *
     * @return the name of the Thymeleaf template to render the translation form
     */
    @GetMapping("/translate")
    public String showTranslateForm() {
        return "translate";
    }

    /**
     * Endpoint to translate text. This method receives a translation request, logs 
     * the client's IP address, and forwards the request to the service layer for processing.
     *
     * @param request        the translation request containing the text and languages
     * @param servletRequest the HTTP servlet request, used to extract the client's IP address
     * @return a {@link ResponseEntity} containing the translation response or an error status
     */
    @PostMapping("/api/translate")
    public ResponseEntity<?> translate(
        @RequestBody YandexApiRequest request, 
        HttpServletRequest servletRequest) {
        var ip = servletRequest.getRemoteAddr();
        log.debug("ip: {}", ip);
        return service.doJob(request, ip);
    }

    /**
     * Handles form submission for translating text via the web interface. 
     * This method builds a {@link YandexApiRequest} object from the form inputs,
     * calls the translation service, and adds the translated text to the model.
     *
     * @param text          the text to be translated
     * @param sourceLang    the source language code (e.g., "en" for English)
     * @param targetLang    the target language code (e.g., "ru" for Russian)
     * @param model         the {@link Model} object to pass data to the view
     * @param servletRequest the HTTP servlet request, used to extract the client's IP address
     * @return the name of the Thymeleaf template to render the translation result
     */
    @PostMapping("/translate")
    public String translate(
            @RequestParam("text") String text,
            @RequestParam("sourceLang") String sourceLang,
            @RequestParam("targetLang") String targetLang,
            Model model, 
            HttpServletRequest servletRequest) {
        
        // Create a YandexApiRequest object with the form data
        YandexApiRequest request = new YandexApiRequest(new String[]{ text }, sourceLang, targetLang);

        // Call the translation service and get the response body
        var body = service.doJob(request, servletRequest.getRemoteAddr()).getBody();
        
        // Add the translated text to the model for rendering in the view
        model.addAttribute("translatedText", body != null ? body : "Failed");
        return "translate";
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
