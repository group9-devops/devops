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
 * These tests verify filtering, printing, and sorting logic
 * without requiring a database connection.
 */
public class AppTest {

    static CapitalCityReport capitalCityReport;

    @BeforeAll
    static void init() {
        // No DB connection required for these tests
        Connection con = null;
        capitalCityReport = new CapitalCityReport(con);
    }

    /**
     * Test that printCapitalCities handles null input without throwing exceptions.
     */
    @Test
    void printCapitalCities_nullInput() {
        try {
            capitalCityReport.printCapitalCities(null);
        } catch (Exception e) {
            fail("printCapitalCities threw an exception on null input: " + e.getMessage());
        }
    }

    /**
     * Test that printCapitalCities handles empty lists without throwing exceptions.
     */
    @Test
    void printCapitalCities_emptyList() {
        try {
            capitalCityReport.printCapitalCities(new ArrayList<>());
        } catch (Exception e) {
            fail("printCapitalCities threw an exception on empty list: " + e.getMessage());
        }
    }

    /**
     * Test that printCapitalCities prints a populated list without throwing exceptions.
     */
    @Test
    void printCapitalCities_withData() {
        try {
            ArrayList<City> capitalCities = new ArrayList<>();
            City c1 = new City(); c1.Name = "Tokyo"; c1.Country = "Japan"; c1.Population = 37400068;
            City c2 = new City(); c2.Name = "Washington D.C."; c2.Country = "USA"; c2.Population = 705749;
            capitalCities.add(c1);
            capitalCities.add(c2);

            capitalCityReport.printCapitalCities(capitalCities);
        } catch (Exception e) {
            fail("printCapitalCities threw an exception on populated list: " + e.getMessage());
        }
    }

    /**
     * Test filtering by country returns only cities from that country.
     */
    @Test
    void filterCitiesByCountry_validCountry() {
        ArrayList<City> cities = new ArrayList<>();
        City c1 = new City(); c1.Name = "Tokyo"; c1.Country = "Japan"; c1.Population = 13929286;
        City c2 = new City(); c2.Name = "Berlin"; c2.Country = "Germany"; c2.Population = 3769000;
        cities.add(c1);
        cities.add(c2);

        ArrayList<City> filtered = capitalCityReport.filterCitiesByCountry(cities, "Japan");

        assertEquals(1, filtered.size(), "Filtered list should contain exactly one city.");
        assertEquals("Tokyo", filtered.get(0).Name, "Filtered city should be Tokyo.");
        assertEquals("Japan", filtered.get(0).Country, "Filtered city should belong to Japan.");
    }

    /**
     * Test filtering by country returns empty list if no match exists.
     */
    @Test
    void filterCitiesByCountry_noMatch() {
        ArrayList<City> cities = new ArrayList<>();
        City c1 = new City(); c1.Name = "Tokyo"; c1.Country = "Japan"; c1.Population = 13929286;
        cities.add(c1);

        ArrayList<City> filtered = capitalCityReport.filterCitiesByCountry(cities, "Germany");

        assertEquals(0, filtered.size(), "Filtered list should be empty when no cities match.");
    }

    /**
     * Test sorting by population manually for a simple list.
     */
    @Test
    void testSortedByPopulation() {
        ArrayList<City> cities = new ArrayList<>();
        City c1 = new City(); c1.Name = "SmallCity"; c1.Country = "A"; c1.Population = 100;
        City c2 = new City(); c2.Name = "BigCity"; c2.Country = "B"; c2.Population = 1000;
        cities.add(c1);
        cities.add(c2);

        // Sort descending by population
        cities.sort((a, b) -> Integer.compare(b.Population, a.Population));

        assertEquals("BigCity", cities.get(0).Name, "First city should be BigCity after sorting.");
        assertEquals("SmallCity", cities.get(1).Name, "Second city should be SmallCity after sorting.");
    }
}





