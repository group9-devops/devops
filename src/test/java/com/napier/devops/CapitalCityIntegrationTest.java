package com.napier.devops;

import com.napier.sem.App;
import com.napier.sem.City;
import com.napier.sem.CapitalCityReport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;



public class CapitalCityIntegrationTest {

    static App app;
    static CapitalCityReport report;

    @BeforeAll
    static void init() {
        app = new App();
        app.connect("localhost:3308", 30000);  // Connect first
        report = new CapitalCityReport(app.con);
    }

    /**
     * test whether the application is able to
     * access the first correct element of cities in the program*/

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
}

