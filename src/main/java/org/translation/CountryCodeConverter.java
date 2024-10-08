package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    private Map<String, String> countryCodeToName;
    private Map<String, String> countryNameToCode;

    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /*
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {

        try {
            countryCodeToName = new HashMap<>();
            countryNameToCode = new HashMap<>();

            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            for (String line : lines.subList(1, lines.size())) {
                String[] parts = line.split("\t");
                String countryName = parts[0].trim();
                String countryCode = parts[1].trim();

                countryCodeToName.put(countryCode, countryName);
                countryNameToCode.put(countryName, countryCode);
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        return countryCodeToName.getOrDefault(code.toUpperCase(), "Unknown Country Code");
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        return countryNameToCode.getOrDefault(country, "Unknown Country");
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        return countryCodeToName.size();
    }
}
