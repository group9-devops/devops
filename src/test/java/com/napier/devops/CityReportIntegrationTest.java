package com.napier.devops;

import com.napier.sem.*;
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
        app.connect("localhost:3308", 30000);  // Connect first
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
     * Test to check for a null continent provided.
     */
    @Test
    void getCitiesByNullContinent() {
        ArrayList<City> city = report.printCitiesByContinent(null);
        assertNotNull(city, "Result should not be null for null region.");
        assertTrue(city.isEmpty(), "Expected empty list for null region.");
    }

    /**
     * Test to check for a null region provided.
     */
    @Test
    void getCitiesByNullRegion() {
        ArrayList<City> city = report.printCitiesByRegion(null);
        assertNotNull(city, "Result should not be null for null region.");
        assertTrue(city.isEmpty(), "Expected empty list for null region.");
    }


    /**
     * Test to check for a null district provided.
     */
    @Test
    void getCitiesByNullDistrict() {
        ArrayList<City> city = report.printCitiesByDistrict(null);
        assertNotNull(city, "Result should not be null for null region.");
        assertTrue(city.isEmpty(), "Expected empty list for null region.");
    }


    /**
     * Integration test for the {@link CityReport#printCities(ArrayList)} method.
     * <p>
     * This test retrieves real city data from the database using
     * {@link CityReport#printAllCities()}  and passes the result
     * to the {@code printCities} method. The purpose is to verify that
     * the method can handle a real dataset without throwing exceptions.
     *
     */
    @Test
    void printCitiesIntegration() {
        ArrayList<City> city = report.printAllCities();
        assertNotNull(city);
        assertFalse(city.isEmpty());

        // Just check that calling the print method does not throw
        report.printCities(city);
    }

    /**
     * Test printing an empty country list.
     */
    @Test
    void printEmptyCityList() {
        ArrayList<City> emptyList = new ArrayList<>();
        report.printCities(emptyList);
    }

    /**
     * Test printing a list with null Country objects.
     */
    @Test
    void printListWithNullCity() {
        ArrayList<City> list = new ArrayList<>();
        list.add(null);
        report.printCities(list);
    }



    /**
     * Test getCountriesByPopulation with a null connection.
     */
    @Test
    void getCitiesByPopulationWithNullConnection() {
        CityReport brokenReport = new CityReport(null);
        ArrayList<City> city = brokenReport.printAllCities();
        assertNotNull(city);
        assertTrue(city.isEmpty(), "Expected empty list when connection is null.");
    }

    /**
     * Test getCountriesByContinent with a null connection.
     */
    @Test
    void getCitiesByContinentWithNullConnection() {
        CityReport brokenReport = new CityReport(null);
        ArrayList<City> city = brokenReport.printCitiesByContinent("Africa");
        assertNotNull(city);
        assertTrue(city.isEmpty(), "Expected empty list when connection is null.");
    }

    /**
     * Test getCountriesByRegion with a null connection.
     */
    @Test
    void getCitiesByRegionWithNullConnection() {
        CityReport brokenReport = new CityReport(null);
        ArrayList<City> city = brokenReport.printCitiesByRegion("Western Europe");
        assertNotNull(city);
        assertTrue(city.isEmpty(), "Expected empty list when connection is null.");
    }


    /**
     * Test getCountriesByRegion with a null connection.
     */
    @Test
    void getCitiesByDistrictWithNullConnection() {
        CityReport brokenReport = new CityReport(null);
        ArrayList<City> city = brokenReport.printCitiesByDistrict("Oran");
        assertNotNull(city);
        assertTrue(city.isEmpty(), "Expected empty list when connection is null.");
    }


}
