package com.napier.devops;

import com.napier.sem.App;
import com.napier.sem.Country;
import com.napier.sem.PrintCountryValues;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for PrintCountryValues class.
 * Verifies that the application can retrieve real data from the MySQL database.
 */
public class PrintCountryValuesIntegrationTest {

    static App app;
    static PrintCountryValues report;

    @BeforeAll
    static void init() {
        app = new App();
        // Connect to the local MySQL instance (use 3308 for GitHub Actions)
        app.connect("localhost:3308", 30000);
        report = new PrintCountryValues(app.con);
    }

    /**
     * Test retrieving all countries ordered by population.
     */
    @Test
    void getAllCountriesByPopulation() {
        ArrayList<Country> countries = report.getCountriesByPopulation();

        assertNotNull(countries, "Country list should not be null.");
        assertFalse(countries.isEmpty(), "Country list should not be empty.");

        Country firstCountry = countries.get(0);
        System.out.printf("Top country: %s (%s) — %,d population%n",
                firstCountry.name, firstCountry.continent, firstCountry.population);

        // Sanity checks
        assertNotNull(firstCountry.name, "Country name should not be null.");
        assertNotNull(firstCountry.continent, "Continent should not be null.");
        assertTrue(firstCountry.population > 0, "Population should be positive.");
    }

    /**
     * Test retrieving countries for a specific continent.
     */
    @Test
    void getAllCountriesByContinent() {
        ArrayList<Country> countries = report.getCountriesByContinent("Asia");

        assertNotNull(countries, "Country list should not be null.");
        assertFalse(countries.isEmpty(), "Country list should not be empty.");

        Country firstCountry = countries.get(0);
        System.out.printf("Top Asian country: %s (%s) — %,d population%n",
                firstCountry.name, firstCountry.region, firstCountry.population);

        // Sanity checks
        assertEquals("Asia", firstCountry.continent, "Continent should be Asia.");
        assertTrue(firstCountry.population > 0, "Population should be positive.");
    }

    /**
     * Test retrieving countries for a specific region.
     */
    @Test
    void getAllCountriesByRegion() {
        ArrayList<Country> countries = report.getCountriesByRegion("Western Europe");

        assertNotNull(countries, "Country list should not be null.");
        assertFalse(countries.isEmpty(), "Country list should not be empty.");

        Country firstCountry = countries.get(0);
        System.out.printf("Top country in Western Europe: %s — %,d population%n",
                firstCountry.name, firstCountry.population);

        // Sanity checks
        assertEquals("Western Europe", firstCountry.region, "Region should be Western Europe.");
        assertTrue(firstCountry.population > 0, "Population should be positive.");
    }

    /**
     * Test to check for the invalid region provided.
     */
    @Test
    void getCountriesByInvalidRegion() {
        ArrayList<Country> countries = report.getCountriesByRegion("Wes");
        assertNotNull(countries);
        assertTrue(countries.isEmpty(), "Expected empty list for non existent region.");
    }
    /**
     * Test to check for the invalid continent provied.
     */
    @Test
    void getCountriesByInvalidContinent() {
        ArrayList<Country> countries = report.getCountriesByContinent("Eur");
        assertNotNull(countries);
        assertTrue(countries.isEmpty(), "Expected empty list for non existent continent.");
    }
}
