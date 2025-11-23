package com.napier.devops;

import com.napier.sem.App;
import com.napier.sem.Country;
import com.napier.sem.CountryReport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for PrintCountryValues class.
 * Verifies that the application can retrieve real data from the MySQL database.
 */
public class CountryReportIntegrationTest {

    static App app;
    static CountryReport report;

    /**
     * Initializes the application and report before all tests.
     * Connects to the database and prepares the Country Report instance.
     */
    @BeforeAll
    static void init() {
        app = new App();
        // Connect to the local MySQL instance (use 3308 for GitHub Actions)
        app.connect("localhost:3308", 30000);
        report = new CountryReport(app.con);
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
                firstCountry.Name, firstCountry.Continent, firstCountry.Population);

        // Sanity checks
        assertNotNull(firstCountry.Name, "Country name should not be null.");
        assertNotNull(firstCountry.Continent, "Continent should not be null.");
        assertTrue(firstCountry.Population > 0, "Population should be positive.");
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
                firstCountry.Name, firstCountry.Region, firstCountry.Population);

        // Sanity checks
        assertEquals("Asia", firstCountry.Continent, "Continent should be Asia.");
        assertTrue(firstCountry.Population > 0, "Population should be positive.");
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
                firstCountry.Name, firstCountry.Population);

        // Sanity checks
        assertEquals("Western Europe", firstCountry.Region, "Region should be Western Europe.");
        assertTrue(firstCountry.Population > 0, "Population should be positive.");
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
     * Test to check for the invalid continent provided.
     */
    @Test
    void getCountriesByInvalidContinent() {
        ArrayList<Country> countries = report.getCountriesByContinent("Eur");
        assertNotNull(countries);
        assertTrue(countries.isEmpty(), "Expected empty list for non existent continent.");
    }

    /**
     * Test to check for a null continent provided.
     */
    @Test
    void getCountriesByNullContinent() {
        ArrayList<Country> countries = report.getCountriesByContinent(null);
        assertNotNull(countries, "Result should not be null for null continent.");
        assertTrue(countries.isEmpty(), "Expected empty list for null continent.");
    }

    /**
     * Test to check for a null region provided.
     */
    @Test
    void getCountriesByNullRegion() {
        ArrayList<Country> countries = report.getCountriesByRegion(null);
        assertNotNull(countries, "Result should not be null for null region.");
        assertTrue(countries.isEmpty(), "Expected empty list for null region.");
    }


    /**
     * Integration test for the {@link CountryReport#printCountries(ArrayList)} method.
     * <p>
     * This test retrieves real country data from the database using
     * {@link CountryReport#getCountriesByPopulation()} and passes the result
     * to the {@code printCountries} method. The purpose is to verify that
     * the method can handle a real dataset without throwing exceptions.
     *
     */
    @Test
    void printCountriesIntegration() {
        ArrayList<Country> countries = report.getCountriesByPopulation();
        assertNotNull(countries);
        assertFalse(countries.isEmpty());

        // Just check that calling the print method does not throw
        report.printCountries(countries);
    }

    /**
     * Test printing an empty country list.
     */
    @Test
    void printEmptyCountryList() {
        ArrayList<Country> emptyList = new ArrayList<>();
        report.printCountries(emptyList);
    }

    /**
     * Test printing a list with null Country objects.
     */
    @Test
    void printListWithNullCountry() {
        ArrayList<Country> list = new ArrayList<>();
        list.add(null);
        report.printCountries(list);
    }



    /**
     * Test getCountriesByPopulation with a null connection.
     */
    @Test
    void getCountriesByPopulationWithNullConnection() {
        CountryReport brokenReport = new CountryReport(null);
        ArrayList<Country> countries = brokenReport.getCountriesByPopulation();
        assertNotNull(countries);
        assertTrue(countries.isEmpty(), "Expected empty list when connection is null.");
    }

    /**
     * Test getCountriesByContinent with a null connection.
     */
    @Test
    void getCountriesByContinentWithNullConnection() {
        CountryReport brokenReport = new CountryReport(null);
        ArrayList<Country> countries = brokenReport.getCountriesByContinent("Asia");
        assertNotNull(countries);
        assertTrue(countries.isEmpty(), "Expected empty list when connection is null.");
    }

    /**
     * Test getCountriesByRegion with a null connection.
     */
    @Test
    void getCountriesByRegionWithNullConnection() {
        CountryReport brokenReport = new CountryReport(null);
        ArrayList<Country> countries = brokenReport.getCountriesByRegion("Western Europe");
        assertNotNull(countries);
        assertTrue(countries.isEmpty(), "Expected empty list when connection is null.");
    }

    /**
     * Test whether printCapitalCities handles a null value*/
    @DisplayName("printCountries handles null list without crashing")
    @Test
    void printNullCapitalCityList() {
        // If this throws an exception, JUnit will fail the test automatically.
        report.printCountries(null);
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
                limit, firstCountry.Name, firstCountry.Population);

        // Sanity check
        assertEquals("Caribbean", firstCountry.Region, "Region should be Caribbean.");
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
                limit, firstCountry.Name, firstCountry.Population);

        // Sanity check
        assertEquals("South America", firstCountry.Continent, "Continent should be South America.");
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
                limit, firstCountry.Name, firstCountry.Population);

        // Sanity check
        assertNotNull(firstCountry.Name, "Country name should not be null.");
        assertTrue(firstCountry.Population > 0, "Population must be positive.");
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
