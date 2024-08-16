package com.pawkorchagin.translate.parser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ParseManager {
    public static String formatText(String responseBody) {
        // Replace any unwanted characters or process the string as needed
        // For example, remove JSON-like wrappers or just return the text if simple
        // Assuming responseBody is something like: "{translations=[{text=text}, {text=amogus}, ...]}"
        // A simple implementation to extract only the text values could be:
    
        // Basic cleaning (this is simplistic, adapt to your actual response format)
        responseBody = responseBody.replace("{translations=[", "")
                                   .replace("]}", "")
                                   .replace("{text=", "")
                                   .replace("}, ", " ")
                                   .replace("}", "");
    
        return responseBody.trim();  // Return cleaned-up text
    }
}
