package com.pawkorchagin.translate.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PrettyMapUtil {

    /**
     * Extracts text values from a nested map structure and returns a pretty-viewed string.
     *
     * @param map The map containing translations data.
     * @return A string with text values joined by a space, or an empty string if no text values are found.
     */
    public static String getPrettyText(Map<String, Object> map) {
        // Check if the map contains a key named "translations"
        if (map.containsKey("translations")) {
            // Extract the value associated with "translations", which is expected to be a List
            Object translationsObject = map.get("translations");

            if (translationsObject instanceof List) {
                // Cast the object to a List
                List<?> translationsList = (List<?>) translationsObject;

                // Extract the "text" from each Map in the list and join them with spaces
                return translationsList.stream()
                        .filter(item -> item instanceof Map) // Ensure each item is a Map
                        .map(item -> ((Map<?, ?>) item).get("text")) // Extract the "text" value
                        .filter(text -> text instanceof String) // Ensure the text is a String
                        .map(Object::toString) // Convert to String
                        .collect(Collectors.joining(" ")); // Join all strings with a space
            }
        }

        // Return an empty string if the map doesn't contain the expected structure
        return "";
    }
}
