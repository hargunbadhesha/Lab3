package org.translation;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the Translator interface which translates
 * the country code "can" to several languages.
 */
public class InLabByHandTranslator implements Translator {
    // Static variable definition should be placed before other methods
    public static final String CANADA = "can";

    /**
     * Returns the language abbreviations for all languages whose translations are
     * available for the given country.
     *
     * @param country the country
     * @return list of language abbreviations which are available for this country
     */

    @Override
    public List<String> getCountryLanguages(String country) {

        if (CANADA.equals(country)) {
            return new ArrayList<>(List.of("de", "en", "zh", "es", "fr"));
        }
        return new ArrayList<>();
    }

    /**
     * Returns the country abbreviations for all countries whose translations are
     * available from this Translator.
     *
     * @return list of country abbreviations for which we have translations available
     */

    @Override
    public List<String> getCountries() {
        return new ArrayList<>(List.of(CANADA));
    }

    /**
     * Returns the name of the country based on the specified country abbreviation and language abbreviation.
     *
     * @param country  the country
     * @param language the language
     * @return the name of the country in the given language or null if no translation is available
     */

    @Override
    public String translate(String country, String language) {
        // Declare a variable to hold the result
        String result = null;

        // Check if the country is CANADA
        if (CANADA.equals(country)) {
            // Check for the language and assign the appropriate translation to the result
            if ("de".equals(language)) {
                result = "Kanada";
            }
            else if ("en".equals(language) || "es".equals(language) || "fr".equals(language)) {
                result = "Canada";
            }
            else if ("zh".equals(language)) {
                result = "加拿大";
            }
        }

        // Return the result (it will be null if no valid translation is found)
        return result;
    }
}
