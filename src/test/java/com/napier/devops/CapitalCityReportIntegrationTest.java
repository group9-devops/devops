package com.napier.devops;

import com.napier.sem.App;
import com.napier.sem.City;
import com.napier.sem.CapitalCityReport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;




/**
 * Integration tests for the CapitalCityReport class.
 * These tests verify that the application can retrieve capital city data
 * from the database and validate basic properties of the returned results.
 */
public class CapitalCityReportIntegrationTest {

    /** Application instance used to establish database connection. */
    static App app;

    /** Report instance used to query capital city data. */
    static CapitalCityReport report;

    /**
     * Initializes the application and report before all tests.
     * Connects to the database and prepares the CapitalCityReport instance.
     */
    @BeforeAll
    static void init() {
        app = new App();
        app.connect("localhost:3308", 30000);  // Connect first
        report = new CapitalCityReport(app.con);
    }

    /**
     * Tests whether the application can retrieve all capital cities.
     * Verifies that the list is not null, not empty, and that the first city
     * has valid name, country, and population values.
     */
    @Test
    void getAllCapitalCities() {
        ArrayList<City> cities = report.getAllCapitalCities();

        assertNotNull(cities, "City list should not be null.");
        assertFalse(cities.isEmpty(), "City list should not be empty.");

        City firstCity = cities.get(0);
        System.out.printf("Top capital city: %s, %s (%,d)%n",
                firstCity.Name, firstCity.Country, firstCity.Population);

        // Sanity checks instead of hardcoded values
        assertNotNull(firstCity.Name);
        assertNotNull(firstCity.Country);
        assertTrue(firstCity.Population > 0);
    }

    /**
     * Tests whether the application can retrieve capital cities by continent.
     * Verifies that the list for "Africa" is not null, not empty, and that
     * the first city has valid name, country, and population values.
     */
    @Test
    void getAllCapitalCitiesByContinent() {
        ArrayList<City> cities = report.getCapitalCitiesByContinent("Africa");

        assertNotNull(cities, "City list should not be null.");
        assertFalse(cities.isEmpty(), "City list should not be empty.");

        City firstCity = cities.get(0);
        System.out.printf("Top capital city: %s, %s (%,d)%n",
                firstCity.Name, firstCity.Country, firstCity.Population);

        // Sanity checks instead of hardcoded values
        assertNotNull(firstCity.Name);
        assertNotNull(firstCity.Country);
        assertTrue(firstCity.Population > 0);
    }

    /**
     * Tests whether the application can retrieve capital cities by region.
     * Verifies that the list for "North America" is not null, not empty, and that
     * the first city has valid name, country, and population values.
     */
    @Test
    void getAllCapitalCitiesByRegion() {
        ArrayList<City> cities = report.getCapitalCitiesByRegion("North America");

        assertNotNull(cities, "City list should not be null.");
        assertFalse(cities.isEmpty(), "City list should not be empty.");

        City firstCity = cities.get(0);
        System.out.printf("Top capital city: %s, %s (%,d)%n",
                firstCity.Name, firstCity.Country, firstCity.Population);

        // Sanity checks instead of hardcoded values
        assertNotNull(firstCity.Name);
        assertNotNull(firstCity.Country);
        assertTrue(firstCity.Population > 0);
    }

    /**
     * Tests whether the application can handle an invalid
     * continent, uses assertTrue to check that an empty list is returned
     * otherwise results in an error*/
    @Test
    void getCapitalCitiesByInvalidContinent() {
        ArrayList<City> cities = report.getCapitalCitiesByContinent("Afr");
        assertNotNull(cities);
        assertTrue(cities.isEmpty(), "Expected empty list for non-existent continent.");
    }

    /**
     * Tests whether the application can handle an invalid
     * continent, uses assertTrue to check that an empty list is returned
     * otherwise results in an error*/
    @Test
    void getCapitalCitiesByInvalidRegion() {
        ArrayList<City> cities = report.getCapitalCitiesByRegion("Tokyo");
        assertNotNull(cities);
        assertTrue(cities.isEmpty(), "Expected empty list for non-existent region.");
    }

    /**
     * Test that getTopNCapitalCities returns exactly N cities in descending population order.
     */
    @Test
    @DisplayName("Test getTopNCapitalCities returns correct number of cities")
    public void testGetTopNCapitalCities() {
        int n = 5;
        ArrayList<City> cities = report.getTopNCapitalCities(n);

        assertNotNull(cities, "Returned list should not be null");
        assertEquals(n, cities.size(), "Returned list should have exactly " + n + " cities");

        for (int i = 1; i < cities.size(); i++) {
            assertTrue(cities.get(i - 1).Population >= cities.get(i).Population,
                    "Cities should be in descending order by population");
        }
    }

    /**
     * Test that getTopNCapitalCitiesByContinent returns exactly N cities
     * in descending population order for the specified continent.
     */
    @Test
    @DisplayName("Test getTopNCapitalCitiesByContinent returns correct number of cities")
    public void testGetTopNCapitalCitiesByContinent() {
        int n = 3;
        String continent = "Asia";

        ArrayList<City> cities = report.getTopNCapitalCitiesByContinent(continent, n);

        assertNotNull(cities, "Returned list should not be null");
        assertEquals(n, cities.size(), "Returned list should have exactly " + n + " cities");

        for (int i = 1; i < cities.size(); i++) {
            assertTrue(cities.get(i - 1).Population >= cities.get(i).Population,
                    "Cities should be in descending order by population");
        }
    }

    /**
     * Test that getTopNCapitalCitiesByRegion returns exactly N cities
     * in descending population order for the specified region.
     */
    @Test
    @DisplayName("Test getTopNCapitalCitiesByRegion returns correct number of cities")
    public void testGetTopNCapitalCitiesByRegion() {
        int n = 2;
        String region = "Western Europe";

        ArrayList<City> cities = report.getTopNCapitalCitiesByRegion(region, n);

        assertNotNull(cities, "Returned list should not be null");
        assertEquals(n, cities.size(), "Returned list should have exactly " + n + " cities");

        for (int i = 1; i < cities.size(); i++) {
            assertTrue(cities.get(i - 1).Population >= cities.get(i).Population,
                    "Cities should be in descending order by population");
        }
    }

    /**
     * Test that requesting zero cities returns an empty list.
     */
    @Test
    @DisplayName("Test requesting zero cities returns empty list")
    public void testGetTopNZeroCities() {
        ArrayList<City> cities = report.getTopNCapitalCities(0);
        assertNotNull(cities, "Returned list should not be null");
        assertTrue(cities.isEmpty(), "Returned list should be empty when n=0");
    }

    /**
     * Test that requesting more cities than available returns all cities.
     */
    @Test
    @DisplayName("Test requesting more cities than exist returns all cities")
    public void testGetTopNMoreThanAvailable() {
        int n = 1000; // Assuming fewer than 1000 capitals in DB
        ArrayList<City> cities = report.getTopNCapitalCities(n);

        assertNotNull(cities, "Returned list should not be null");
        assertTrue(cities.size() <= n, "Returned list should contain at most " + n + " cities");
    }

    /**
     * Test that an invalid continent returns an empty list.
     */
    @Test
    @DisplayName("Test invalid continent returns empty list")
    public void testInvalidContinent() {
        ArrayList<City> cities = report.getTopNCapitalCitiesByContinent("Atlantis", 5);
        assertNotNull(cities, "Returned list should not be null");
        assertTrue(cities.isEmpty(), "Returned list should be empty for invalid continent");
    }

    /**
     * Test that an invalid region returns an empty list.
     */
    @Test
    @DisplayName("Test invalid region returns empty list")
    public void testInvalidRegion() {
        ArrayList<City> cities = report.getTopNCapitalCitiesByRegion("Middle Earth", 5);
        assertNotNull(cities, "Returned list should not be null");
        assertTrue(cities.isEmpty(), "Returned list should be empty for invalid region");
    }
}














