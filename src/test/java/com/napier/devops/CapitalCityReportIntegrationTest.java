package com.napier.devops;

import com.napier.sem.App;
import com.napier.sem.City;
import com.napier.sem.CapitalCityReport;
import com.napier.sem.CityReport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;



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
     * Test to check for a null continent provided.
     */
    @Test
    void getCountriesByNullContinent() {
        ArrayList<City> city = report.getCapitalCitiesByContinent(null);
        assertNotNull(city, "Result should not be null for null region.");
        assertTrue(city.isEmpty(), "Expected empty list for null region.");
    }

    /**
     * Test to check for a null region provided.
     */
    @Test
    void getCountriesByNullRegion() {
        ArrayList<City> city = report.getCapitalCitiesByRegion(null);
        assertNotNull(city, "Result should not be null for null region.");
        assertTrue(city.isEmpty(), "Expected empty list for null region.");
    }




    /**
     * Integration test for the {@link CapitalCityReport#printCapitalCities(ArrayList)} method.
     * <p>
     * This test retrieves real city data from the database using
     * {@link CapitalCityReport#getAllCapitalCities()}  and passes the result
     * to the {@code printCities} method. The purpose is to verify that
     * the method can handle a real dataset without throwing exceptions.
     *
     */
    @Test
    void printCapitalCitiesIntegration() {
        ArrayList<City> city = report.getAllCapitalCities();
        assertNotNull(city);
        assertFalse(city.isEmpty());

        // Just check that calling the print method does not throw
        report.printCapitalCities(city);
    }

    /**
     * Test printing an empty country list.
     */
    @Test
    void printEmptyCityList() {
        ArrayList<City> emptyList = new ArrayList<>();
        report.printCapitalCities(emptyList);
    }

    /**
     * Test printing a list with null Country objects.
     */
    @Test
    void printListWithNullCity() {
        ArrayList<City> list = new ArrayList<>();
        list.add(null);
        report.printCapitalCities(list);
    }



    /**
     * Test getCountriesByPopulation with a null connection.
     */
    @Test
    void getCitiesByPopulationWithNullConnection() {
        CapitalCityReport brokenReport = new CapitalCityReport(null);
        ArrayList<City> city = brokenReport.getAllCapitalCities();
        assertNotNull(city);
        assertTrue(city.isEmpty(), "Expected empty list when connection is null.");
    }

    /**
     * Test getCountriesByContinent with a null connection.
     */
    @Test
    void getCitiesByContinentWithNullConnection() {
        CapitalCityReport brokenReport = new CapitalCityReport(null);
        ArrayList<City> city = brokenReport.getCapitalCitiesByContinent("Europe");
        assertNotNull(city);
        assertTrue(city.isEmpty(), "Expected empty list when connection is null.");
    }

    /**
     * Test getCountriesByRegion with a null connection.
     */
    @Test
    void getCitiesByRegionWithNullConnection() {
        CapitalCityReport brokenReport = new CapitalCityReport(null);
        ArrayList<City> city = brokenReport.getCapitalCitiesByRegion("North America");
        assertNotNull(city);
        assertTrue(city.isEmpty(), "Expected empty list when connection is null.");
    }


}

