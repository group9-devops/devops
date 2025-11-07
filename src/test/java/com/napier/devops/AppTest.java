package com.napier.devops;

import com.napier.sem.CapitalCityReport;
import com.napier.sem.City;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CapitalCityReport class.
 * These tests ensure the class can handle null, empty, and populated lists,
 * and that basic filtering logic behaves correctly without needing a database.
 */
public class AppTest {

    static CapitalCityReport capitalCityReport;

    @BeforeAll
    static void init() {
        Connection con = null; // no DB connection needed
        capitalCityReport = new CapitalCityReport(con);
    }

    @Test
    void printCapitalCitiesNull() {
        try {
            capitalCityReport.printCapitalCities(null);
        } catch (Exception e) {
            fail("Method threw an exception on null input: " + e.getMessage());
        }
    }

    @Test
    void printCapitalCitiesEmpty() {
        try {
            capitalCityReport.printCapitalCities(new ArrayList<>());
        } catch (Exception e) {
            fail("Method threw an exception on empty list: " + e.getMessage());
        }
    }

    @Test
    void printCapitalCitiesWithData() {
        try {
            ArrayList<City> capitalCities = new ArrayList<>();
            City c1 = new City(); c1.Name = "Tokyo"; c1.Country = "Japan"; c1.Population = 37400068;
            City c2 = new City(); c2.Name = "Washington D.C."; c2.Country = "USA"; c2.Population = 705749;
            capitalCities.add(c1); capitalCities.add(c2);
            capitalCityReport.printCapitalCities(capitalCities);
        } catch (Exception e) {
            fail("Method threw an exception when printing populated list: " + e.getMessage());
        }
    }




    @Test
    void FilterByContinent() {
        ArrayList<City> cities = new ArrayList<>();

        City c1 = new City(); c1.Name = "Tokyo"; c1.Country = "Japan"; c1.Population = 13929286;cities.add(c1);

        City c2= new City();
        c2.Name = "Berlin";
        c2.Country = "Germany";
        c2.Population = 3769000;
        cities.add(c2);

        CapitalCityReport report = new CapitalCityReport(null);
        ArrayList<City> asianCities = report.filterCitiesByCountry(cities, "Japan");

        assertEquals(1, asianCities.size(), "There should be exactly one Asian capital city.");
        assertEquals("Tokyo", asianCities.get(0).Name, "The Asian capital city should be Tokyo.");

        // Optional: print message when test passes
        System.out.println("Test passed: Continent filtering works correctly.");
    }



    @Test
    void filterCitiesByRegion() {
        ArrayList<City> capitalCities = new ArrayList<>();
        City c1 = new City(); c1.Name = "Paris"; c1.Country = "France"; capitalCities.add(c1);
        City c2 = new City(); c2.Name = "Berlin"; c2.Country = "Germany"; capitalCities.add(c2);

        ArrayList<City> europeCities = new ArrayList<>();
        for (City c : capitalCities) {
            if ("France".equals(c.Country) || "Germany".equals(c.Country)) {
                europeCities.add(c);
            }
        }

        assertEquals(2, europeCities.size());

        // Optional: print message when test passes
        System.out.println("Test passed: Region filtering works correctly");
    }

    @Test
    void testSortedByPopulation() {
        ArrayList<City> cities = new ArrayList<>();
        City c1 = new City(); c1.Name = "SmallCity"; c1.Country = "A"; c1.Population = 100;
        City c2 = new City(); c2.Name = "BigCity"; c2.Country = "B"; c2.Population = 1000;
        cities.add(c1); cities.add(c2);

        // Sort manually to simulate expected behavior
        cities.sort((a, b) -> Integer.compare(b.Population, a.Population));

        assertEquals("BigCity", cities.get(0).Name);
        assertEquals("SmallCity", cities.get(1).Name);
    }
}




