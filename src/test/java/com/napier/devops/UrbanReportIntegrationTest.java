package com.napier.devops;

import com.napier.sem.App;
import com.napier.sem.UrbanReport;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for {@link UrbanReport} using a live database connection.
 * <p>
 * These tests verify that SQL queries execute successfully against a real
 * instance of the World database and that returned values are valid and
 * non-zero. They do not mock database behaviour—this ensures full end-to-end
 * validation of UrbanReport’s data retrieval methods.
 */

public class UrbanReportIntegrationTest {
    static App a;
    static UrbanReport report;

    /**
     * Establishes a real database connection before any tests run.
     * Assumes the MySQL instance is reachable on localhost:3308.
     */
    @BeforeAll
    static void init() {
        a = new App();
        a.connect("localhost:3308", 30000);
        report = new UrbanReport();
    }

    /**
     * Closes the database connection after all integration tests complete.
     */
    @AfterAll
    static void close() {
        a.disconnect();
    }

    /**
     * Tests retrieval of total world population.
     * Ensures the returned population is valid and greater than zero.
     */
    @Test
    void testGetPopulationOfWorld() {
        report.getPopulationOfWorld(a.con);

        System.out.println("World Population = " + report.population);
        assertTrue(report.population > 0,
                "World population should be greater than zero");
    }

    /**
     * Tests retrieval of the total urban population of the world.
     * World population is queried first to ensure consistency.
     */
    @Test
    void testGetUrbanPopulation() {
        report.getPopulationOfWorld(a.con);
        report.getUrbanPopulation(a.con);

        System.out.println("Urban Population = " + report.urbanPopulation);

        assertTrue(report.urbanPopulation > 0,
                "Urban population should be greater than zero");
    }

    /**
     * Tests retrieval of total and urban population for a specific region.
     * Uses the region "British Islands" which exists in the World database.
     */
    @Test
    void testGetPopulationOfRegion() {
        report.getUrbanPopulationOfRegion(a.con, "British Islands");
        report.getPopulationOfRegion(a.con, "British Islands");

        System.out.println("Region Population = " + report.population);
        System.out.println("Region Urban Population = " + report.urbanPopulation);

        assertTrue(report.population > 0,
                "Region population should be greater than zero");
        assertTrue(report.urbanPopulation > 0,
                "Urban regional population should be greater than zero");
    }

    /**
     * Tests retrieval of total and urban population for a specific country.
     * Uses Afghanistan as it is guaranteed to exist in the database.
     */
    @Test
    void testGetPopulationOfCountry() {
        report.getPopulationOfCountry(a.con, "Afghanistan");
        report.getUrbanPopulationOfCountry(a.con, "Afghanistan");

        System.out.println("Country Population = " + report.population);
        System.out.println("Country Urban Population = " + report.urbanPopulation);

        assertTrue(report.population > 0,
                "Country population should be greater than zero");
        assertTrue(report.urbanPopulation > 0,
                "Urban country population should be greater than zero");
    }

    /**
     * Tests retrieval of total and urban population for a specific continent.
     * Uses Asia due to its high population and reliable presence in the dataset.
     */
    @Test
    void testGetPopulationOfContinent() {
        report.getPopulationOfContinent(a.con, "Asia");
        report.getUrbanPopulationOfContinent(a.con, "Asia");

        System.out.println("Continent Population = " + report.population);
        System.out.println("Continent Urban Population = " + report.urbanPopulation);

        assertTrue(report.population > 0,
                "Continent population should be greater than zero");
        assertTrue(report.urbanPopulation > 0,
                "Urban continent population should be greater than zero");
    }

    /**
     * Tests retrieval of population for a specific city.
     * Uses Liverpool, which is guaranteed to appear in the city table.
     */
    @Test
    void testGetPopulationOfCity() {
        report.getPopulationOfCity(a.con, "Liverpool");

        System.out.println("City Population = " + report.population);

        assertTrue(report.population > 0,
                "City population should be greater than zero");
    }
}
