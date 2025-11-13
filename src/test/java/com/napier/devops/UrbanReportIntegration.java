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
        a.connect();  // Connect first
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
        System.out.println("Percentage Urban = " + report.percentage);

        assertTrue(report.urbanPopulation > 0, "Urban population should be greater than zero");
        assertTrue(report.percentage > 0 && report.percentage <= 100, "Urban percentage should be between 0 and 100");
    }
}
