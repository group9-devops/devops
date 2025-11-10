package com.napier.devops;

import com.napier.sem.PrintCountryValues;
import com.napier.sem.Country;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the PrintCountryValues class.
 * These tests ensure the class can handle null, empty, and populated lists
 */
public class AppTest {

    static PrintCountryValues printer;

    @BeforeAll
    static void init() {
        Connection con = null;
        printer = new PrintCountryValues(con);
    }

    @Test
    void testPrintCountriesNull() {
        try {
            printer.printCountries(null);
        } catch (Exception e) {
            fail("Method threw an exception on null input: " + e.getMessage());
        }
    }

    @Test
    void testPrintCountriesEmpty() {
        try {
            printer.printCountries(new ArrayList<>());
        } catch (Exception e) {
            fail("Method threw an exception on empty list: " + e.getMessage());
        }
    }

    @Test
    void testPrintCountriesWithData() {
        try {
            ArrayList<Country> countries = new ArrayList<>();

            // Create some dummy data
            Country c1 = new Country();
            c1.name = "Aruba";
            c1.continent = "North America";
            c1.region = "Caribbean";
            c1.capital = "Beatrix";
            c1.code = "ABW";
            c1.population = 103000;
            countries.add(c1);

            Country c2 = new Country();
            c2.name = "Afghanistan";
            c2.continent = "Asia";
            c2.region = "Southern and Central Asia";
            c2.capital = "Kabul";
            c2.code = "AFG";
            c2.population = 652090;
            countries.add(c2);

            // Run the print method
            printer.printCountries(countries);

        } catch (Exception e) {
            fail("Method threw an exception when printing populated list: " + e.getMessage());
        }
    }
}