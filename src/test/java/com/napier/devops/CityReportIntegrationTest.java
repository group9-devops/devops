package com.napier.devops;

import com.napier.sem.App;
import com.napier.sem.City;
import com.napier.sem.CityReport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the {@link CityReport} class.
 * These tests verify that the application can retrieve city data
 * from the database and validate basic properties of the returned results.
 */
public class CityReportIntegrationTest {

    /** Application instance used to establish database connection. */
    static App app;

    /** Report instance used to query city data. */
    static CityReport report;

    /**
     * Initializes the application and report before all tests.
     * Connects to the database and prepares the CityReport instance.
     */
    @BeforeAll
    static void init() {
        app = new App();
        app.connect("localhost:3307", 30000);  // Connect first
        report = new CityReport(app.con);
    }

    /**
     * Tests whether the application can retrieve all cities.
     * Verifies that the list is not null, not empty, and that the first city
     * has valid name, country, district, and population values.
     */
    @Test
    void getAllCities() {
        ArrayList<City> cities = report.printAllCities();

        assertNotNull(cities, "City list should not be null.");
        assertFalse(cities.isEmpty(), "City list should not be empty.");

        City firstCity = cities.get(0);
        System.out.printf("Top city: %s, %s, %s (%,d)%n",
                firstCity.Name, firstCity.Country, firstCity.District, firstCity.Population);

        // Sanity checks
        assertNotNull(firstCity.Name);
        assertNotNull(firstCity.Country);
        assertNotNull(firstCity.District);
        assertTrue(firstCity.Population > 0);
    }

    /**
     * Tests whether the application can retrieve cities by continent.
     * Verifies that the list for "Asia" is not null, not empty, and that
     * the first city has valid name, country, district, and population values.
     */
    @Test
    void getCitiesByContinent() {
        ArrayList<City> cities = report.printCitiesByContinent("Asia");

        assertNotNull(cities, "City list should not be null.");
        assertFalse(cities.isEmpty(), "City list should not be empty.");

        City firstCity = cities.get(0);
        System.out.printf("Top city in Asia: %s, %s, %s (%,d)%n",
                firstCity.Name, firstCity.Country, firstCity.District, firstCity.Population);

        assertNotNull(firstCity.Name);
        assertNotNull(firstCity.Country);
        assertNotNull(firstCity.District);
        assertTrue(firstCity.Population > 0);
    }

    /**
     * Tests whether the application can retrieve cities by region.
     * Verifies that the list for "Western Europe" is not null, not empty, and that
     * the first city has valid name, country, district, and population values.
     */
    @Test
    void getCitiesByRegion() {
        ArrayList<City> cities = report.printCitiesByRegion("Western Europe");

        assertNotNull(cities, "City list should not be null.");
        assertFalse(cities.isEmpty(), "City list should not be empty.");

        City firstCity = cities.get(0);
        System.out.printf("Top city in Western Europe: %s, %s, %s (%,d)%n",
                firstCity.Name, firstCity.Country, firstCity.District, firstCity.Population);

        assertNotNull(firstCity.Name);
        assertNotNull(firstCity.Country);
        assertNotNull(firstCity.District);
        assertTrue(firstCity.Population > 0);
    }

    /**
     * Tests whether the application can retrieve cities by district.
     * Verifies that the list for "Tokyo-to" is not null, not empty, and that
     * the first city has valid name, country, district, and population values.
     */
    @Test
    void getCitiesByDistrict() {
        ArrayList<City> cities = report.printCitiesByDistrict("Tokyo-to");

        assertNotNull(cities, "City list should not be null.");
        assertFalse(cities.isEmpty(), "City list should not be empty.");

        City firstCity = cities.get(0);
        System.out.printf("Top city in Tokyo-to: %s, %s, %s (%,d)%n",
                firstCity.Name, firstCity.Country, firstCity.District, firstCity.Population);

        assertNotNull(firstCity.Name);
        assertNotNull(firstCity.Country);
        assertNotNull(firstCity.District);
        assertTrue(firstCity.Population > 0);
    }

    /**
     * Tests whether the application handles an invalid continent correctly.
     * Expects an empty list of cities to be returned.
     */
    @Test
    void getCitiesByInvalidContinent() {
        ArrayList<City> cities = report.printCitiesByContinent("Atlantis");
        assertNotNull(cities);
        assertTrue(cities.isEmpty(), "Expected empty list for non-existent continent.");
    }

    /**
     * Tests whether the application handles an invalid region correctly.
     * Expects an empty list of cities to be returned.
     */
    @Test
    void getCitiesByInvalidRegion() {
        ArrayList<City> cities = report.printCitiesByRegion("NowhereLand");
        assertNotNull(cities);
        assertTrue(cities.isEmpty(), "Expected empty list for non-existent region.");
    }

    /**
     * Tests whether the application handles an invalid district correctly.
     * Expects an empty list of cities to be returned.
     */
    @Test
    void getCitiesByInvalidDistrict() {
        ArrayList<City> cities = report.printCitiesByDistrict("UnknownDistrict");
        assertNotNull(cities);
        assertTrue(cities.isEmpty(), "Expected empty list for non-existent district.");
    }

    /**
     * Test that getTopNCities returns exactly N cities in descending population order.
     */
    @Test
    public void testGetTopNCities() {
        int n = 5;
        ArrayList<City> cities = report.getTopNCitiesInWorld(n);

        assertNotNull(cities, "Returned list should not be null");
        assertEquals(n, cities.size(), "Returned list should have exactly " + n + " cities");

        for (int i = 1; i < cities.size(); i++) {
            assertTrue(cities.get(i - 1).Population >= cities.get(i).Population,
                    "Cities should be in descending order by population");
        }
    }

    /**
     * Test that getTopNCitiesByContinent returns exactly N cities
     * in descending population order for the specified continent.
     */
    @Test
    public void testGetTopNCitiesByContinent() {
        int n = 3;
        String continent = "Asia";

        ArrayList<City> cities = report.getTopNCitiesByContinent(continent, n);

        assertNotNull(cities, "Returned list should not be null");
        assertEquals(n, cities.size(), "Returned list should have exactly " + n + " cities");

        for (int i = 1; i < cities.size(); i++) {
            assertTrue(cities.get(i - 1).Population >= cities.get(i).Population,
                    "Cities should be in descending order by population");
        }
    }

    /**
     * Test that getTopNCitiesByRegion returns exactly N cities
     * in descending population order for the specified region.
     */
    @Test
    public void testGetTopNCitiesByRegion() {
        int n = 2;
        String region = "Western Europe";

        ArrayList<City> cities = report.getTopNCitiesByRegion(region, n);

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
    public void testGetTopNZeroCities() {
        ArrayList<City> cities = report.getTopNCitiesInWorld(0);
        assertNotNull(cities, "Returned list should not be null");
        assertTrue(cities.isEmpty(), "Returned list should be empty when n=0");
    }

    /**
     * Test that requesting more cities than available returns all cities.
     */
    @Test
    public void testGetTopNMoreThanAvailable() {
        int n = 1000; // Assuming fewer than 1000 capitals in DB
        ArrayList<City> cities = report.getTopNCitiesInWorld(n);

        assertNotNull(cities, "Returned list should not be null");
        assertTrue(cities.size() <= n, "Returned list should contain at most " + n + " cities");
    }

    /**
     * Test that an invalid continent returns an empty list.
     */
    @Test
    public void testInvalidContinent() {
        ArrayList<City> cities = report.getTopNCitiesByContinent("Atlantis", 5);
        assertNotNull(cities, "Returned list should not be null");
        assertTrue(cities.isEmpty(), "Returned list should be empty for invalid continent");
    }

    /**
     * Test that an invalid region returns an empty list.
     */
    @Test
    public void testInvalidRegion() {
        ArrayList<City> cities = report.getTopNCitiesByRegion("Middle Earth", 5);
        assertNotNull(cities, "Returned list should not be null");
        assertTrue(cities.isEmpty(), "Returned list should be empty for invalid region");
    }

    /**
     * Test that an invalid region returns an empty list.
     */
    @Test
    public void testInvalidDistrict() {
        ArrayList<City> cities = report.getTopNCitiesByDistrict("Middle Earth", 5);
        assertNotNull(cities, "Returned list should not be null");
        assertTrue(cities.isEmpty(), "Returned list should be empty for invalid region");
    }
}
