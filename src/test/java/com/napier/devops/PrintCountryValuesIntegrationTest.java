package com.napier.devops;

import com.napier.sem.App;

import com.napier.sem.PrintCountryValues;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class PrintCountryValuesIntegrationTest {

    static App app;
    static PrintCountryValues report;

    @BeforeAll
    static void init() {
        app = new App();
        app.connect("localhost:3308", 30000);  // Connect first
        report = new PrintCountryValues(app.con);
    }

    @Test
    void getAllCapitalCities() {
        System.out.println("getAllCapitalCities");
    }
}