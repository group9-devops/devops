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
public class CountryReportIntegrationTest {

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






    /**
     * Test retrieving top N countries for a specific region.
     */
    @Test
    void testGetTopNCountriesByRegion() {
        int limit = 5;
        ArrayList<Country> countries = report.topNCountriesByRegion("Caribbean", limit);

        assertNotNull(countries, "Country list should not be null.");
        assertFalse(countries.isEmpty(), "Country list should not be empty.");

        // Check that the limit was respected
        assertTrue(countries.size() <= limit, "List size should be less than or equal to the limit.");

        Country firstCountry = countries.get(0);
        System.out.printf("Top %d country in Caribbean: %s — %,d population%n",
                limit, firstCountry.name, firstCountry.population);

        // Sanity check
        assertEquals("Caribbean", firstCountry.region, "Region should be Caribbean.");
    }

    /**
     * Test retrieving top N countries for a specific continent.
     */
    @Test
    void testGetTopNCountriesByContinent() {
        int limit = 3;
        ArrayList<Country> countries = report.topNCountriesByContinent("South America", limit);

        assertNotNull(countries, "Country list should not be null.");
        assertFalse(countries.isEmpty(), "Country list should not be empty.");

        // Check that the limit was respected
        assertTrue(countries.size() <= limit, "List size should be less than or equal to the limit.");

        Country firstCountry = countries.get(0);
        System.out.printf("Top %d country in South America: %s — %,d population%n",
                limit, firstCountry.name, firstCountry.population);

        // Sanity check
        assertEquals("South America", firstCountry.continent, "Continent should be South America.");
    }

    /**
     * Test retrieving top N countries in the world.
     */
    @Test
    void testGetTopNCountriesInTheWorld() {
        int limit = 10;
        ArrayList<Country> countries = report.topNCountriesInTheWorld(limit);

        assertNotNull(countries, "Country list should not be null.");
        assertFalse(countries.isEmpty(), "Country list should not be empty.");

        // This is a strong test that LIMIT ? worked correctly.
        // It assumes your database has at least 10 countries.
        assertEquals(limit, countries.size(), "List size should be equal to the limit.");

        Country firstCountry = countries.get(0);
        System.out.printf("Top %d country in the world: %s — %,d population%n",
                limit, firstCountry.name, firstCountry.population);

        // Sanity check
        assertNotNull(firstCountry.name, "Country name should not be null.");
        assertTrue(firstCountry.population > 0, "Population must be positive.");
    }

    /**
     * Test top N countries with an invalid region.
     */
    @Test
    void testGetTopNCountriesByInvalidRegion() {
        ArrayList<Country> countries = report.topNCountriesByRegion("NotARealRegion", 5);
        assertNotNull(countries);
        assertTrue(countries.isEmpty(), "Expected empty list for non-existent region.");
    }

    /**
     * Test top N countries with an invalid continent.
     */
    @Test
    void testGetTopNCountriesByInvalidContinent() {
        ArrayList<Country> countries = report.topNCountriesByContinent("NotARealContinent", 5);
        assertNotNull(countries);
        assertTrue(countries.isEmpty(), "Expected empty list for non-existent continent.");
    }
}
