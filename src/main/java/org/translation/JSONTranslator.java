package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    // A map where the key is the country code, and the value is another map of language codes to translations
    private Map<String, Map<String, String>> countryTranslations;

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     *
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        countryTranslations = new HashMap<>();

        try {
            // Read the JSON file as a string
            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            // Parse the string into a JSONArray
            JSONArray jsonArray = new JSONArray(jsonString);

            // Iterate over the JSON array and populate the countryTranslations map
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryObject = jsonArray.getJSONObject(i);

                String countryCode = countryObject.getString("alpha3");
                Map<String, String> languageMap = new HashMap<>();

                // Loop through the keys in the countryObject (each key is a language code, except "alpha3" and "id")
                for (String key : countryObject.keySet()) {
                    if (!"alpha3".equals(key) && !"id".equals(key)) {
                        // Add the language code and its corresponding translation to the languageMap
                        languageMap.put(key, countryObject.getString(key));
                    }
                }

                // Add the country and its translations to the countryTranslations map
                countryTranslations.put(countryCode, languageMap);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns a list of language codes available for the given country.
     *
     * @param country the 3-letter country code
     * @return list of language codes for the country
     */
    @Override
    public List<String> getCountryLanguages(String country) {
        if (countryTranslations.containsKey(country)) {
            // Return the list of language codes available for this country
            return new ArrayList<>(countryTranslations.get(country).keySet());
        }
        return new ArrayList<>();
    }

    /**
     * Returns a list of country codes available in the translator.
     *
     * @return list of country codes
     */
    @Override
    public List<String> getCountries() {
        // Return the list of country codes (keys in the countryTranslations map)
        return new ArrayList<>(countryTranslations.keySet());
    }

    /**
     * Translates the given country code into the specified language.
     *
     * @param country  the 3-letter country code
     * @param language the 2-letter language code
     * @return the translated name of the country in the given language
     */
    @Override
    public String translate(String country, String language) {
        // Declare the result variable with a default value
        String result = "Country not found";

        // Check if the country exists
        if (countryTranslations.containsKey(country)) {
            Map<String, String> translations = countryTranslations.get(country);

            // Check if the language exists for the country
            if (translations.containsKey(language)) {
                result = translations.get(language);
            }
            else {
                result = "Translation not available";
            }
        }

        // Return the result (either a translation or a default message)
        return result;
    }
}
