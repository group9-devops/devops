package com.napier.devops;

import com.napier.sem.App;
import com.napier.sem.CapitalCityReport;
import com.napier.sem.CityReport;
import com.napier.sem.CountryReport;
import org.junit.jupiter.api.Test;

/**
 * Integration tests for the App class.
 * Connects to a real database and verifies that runReports
 * executes without exceptions using actual data.
 */

class AppIntegrationTest {

    /**
     * Executes runReports with real report instances and database.
     * Checks that the workflow completes without exceptions.
     */
    @Test
    void testRunReports() {
        App app = new App();
        app.connect("localhost:3308", 0);

        CityReport cityReport = new CityReport(app.con);
        CapitalCityReport capitalReport = new CapitalCityReport(app.con);
        CountryReport PrintCountry = new CountryReport(app.con);

        app.runReports(cityReport, capitalReport, PrintCountry); // test fails if exception occurs

        app.disconnect();
    }

}
