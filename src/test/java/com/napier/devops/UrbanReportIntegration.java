package com.napier.devops;

import com.napier.sem.App;
import com.napier.sem.UrbanReport;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UrbanReportIntegration
{
    static App a;
    static UrbanReport report;

    @BeforeAll
    static void init() {
        a = new App();
        a.connect("localhost:3308", 30000);  // Connect first
        report = new UrbanReport();
    }

    @AfterAll
    static void close()
    {
        a.disconnect();
    }

    @Test
    void testGetPopulationOfWorld() {
        report.getPopulationOfWorld(a.con);

        System.out.println("World Population = " + report.population);
        assertTrue(report.population > 0, "World population should be greater than zero");
    }

    @Test
    void testGetUrbanPopulation() {
        // Must get world population first
        report.getPopulationOfWorld(a.con);
        report.getUrbanPopulation(a.con);

        System.out.println("Urban Population = " + report.urbanPopulation);

        assertTrue(report.urbanPopulation > 0, "Urban population should be greater than zero");
    }

    @Test
    void testGetPopulationOfRegion(){
        report.getUrbanPopulationOfRegion(a.con,"British Islands");
        report.getPopulationOfRegion(a.con,"British Islands");

        System.out.println("Population = " + report.population);
        System.out.println("Urban Population = " + report.urbanPopulation);

        assertTrue(report.population > 0, "Population should be greater than zero");
        assertTrue(report.urbanPopulation > 0, "Urban population should be greater than zero");
    }

    @Test
    void testGetPopulationOfCountry(){
        report.getPopulationOfCountry(a.con,"Afghanistan");
        report.getUrbanPopulationOfCountry(a.con,"Afghanistan");

        System.out.println("Population = " + report.population);
        System.out.println("Urban Population = " + report.urbanPopulation);

        assertTrue(report.population > 0, "Population should be greater than zero");
        assertTrue(report.urbanPopulation > 0, "Urban population should be greater than zero");
    }

    @Test
    void testGetPopulationOfContinent(){
        report.getPopulationOfContinent(a.con,"Asia");
        report.getUrbanPopulationOfContinent(a.con,"Asia");

        System.out.println("Population = " + report.population);
        System.out.println("Urban Population = " + report.urbanPopulation);

        assertTrue(report.population > 0, "Population should be greater than zero");
        assertTrue(report.urbanPopulation > 0, "Urban population should be greater than zero");
    }

    @Test
    void testGetPopulationOfCity(){
        report.getPopulationOfCity(a.con,"Liverpool");

        System.out.println("Population = " + report.population);

        assertTrue(report.population > 0, "Population should be greater than zero");
    }
}
