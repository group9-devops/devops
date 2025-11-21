package com.napier.devops;

import com.napier.sem.App;
import com.napier.sem.LanguageReport;
import com.napier.sem.CountryLanguage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

/**
 * Integration tests for the LanguageReport class.
 * These tests verify that the application can retrieve language speaker data
 * from the database and validate basic properties of the returned results.
 */
public class LanguageReportIntegrationTest {

    /** Application instance used to establish database connection. */
    static App app;

    /** Report instance used to query language data. */
    static LanguageReport report;

    /**
     * Initializes the application and report before all tests.
     * Connects to the database and prepares the LanguageReport instance.
     */
    @BeforeAll
    static void init() {
        app = new App();
        app.connect("localhost:3308", 30000);  // Connect first
        report = new LanguageReport(app.con);
    }

    /**
     * Tests whether the application can retrieve all selected languages.
     * Verifies that the list is not null, not empty, and that the first language
     * has valid fields.
     */
    @Test
    void getAllLanguages() {
        ArrayList<CountryLanguage> languages = report.retrieveLanguageSpeakers();

        assertNotNull(languages, "Language list should not be null.");
        assertFalse(languages.isEmpty(), "Language list should not be empty.");

        CountryLanguage firstLang = languages.get(0);
        System.out.printf("Top language: %s, %,d speakers (%.2f%% of world)%n",
                firstLang.Language, firstLang.NumberOfSpeakers, firstLang.WorldPercentage);

        // Sanity checks instead of hardcoded values
        assertNotNull(firstLang.Language);
        assertTrue(firstLang.NumberOfSpeakers > 0);
        assertTrue(firstLang.WorldPercentage > 0);
    }

    /**
     * Tests that printLanguageReport handles null correctly.
     */
    @Test
    void testPrintLanguageReport_Null() {
        report.printLanguageReport(null);
    }

    /**
     * Tests that printLanguageReport handles empty list correctly.
     */
    @Test
    void testPrintLanguageReport_Empty() {
        report.printLanguageReport(new ArrayList<>());
    }

    /**
     * Tests that printLanguageReport prints a valid list correctly.
     */
    @Test
    void testPrintLanguageReport_ValidList() {
        CountryLanguage l1 = new CountryLanguage();
        l1.Language = "Chinese";
        l1.NumberOfSpeakers = 1140000000L;
        l1.WorldPercentage = 13.9;

        CountryLanguage l2 = new CountryLanguage();
        l2.Language = "English";
        l2.NumberOfSpeakers = 1450000000L;
        l2.WorldPercentage = 17.6;

        ArrayList<CountryLanguage> languages = new ArrayList<>();
        languages.add(l1);
        languages.add(l2);

        report.printLanguageReport(languages);
    }
}
