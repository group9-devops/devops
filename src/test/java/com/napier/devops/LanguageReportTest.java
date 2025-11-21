package com.napier.devops;

import com.napier.sem.LanguageReport;
import com.napier.sem.CountryLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link LanguageReport} class.
 * This test class uses Mockito to mock database interactions and verify
 * the behavior of methods that retrieve language speaker data from a database.
 */
class LanguageReportTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private LanguageReport report;

    @BeforeEach
    void setUp() throws Exception {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        report = new LanguageReport(mockConnection);
    }

    /**
     * Verifies that details of an expected language are returned.
     */
    @Test
    void testRetrieveLanguageSpeakers_Valid() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("Language")).thenReturn("English");
        when(mockResultSet.getLong("NumberOfSpeakers")).thenReturn(1450000000L);
        when(mockResultSet.getDouble("WorldPercentage")).thenReturn(17.6);

        ArrayList<CountryLanguage> languages = report.retrieveLanguageSpeakers();

        assertNotNull(languages);
        assertEquals(1, languages.size());
        assertEquals("English", languages.get(0).Language);
        assertEquals(1450000000L, languages.get(0).NumberOfSpeakers);
        assertEquals(17.6, languages.get(0).WorldPercentage);
    }

    /**
     * Verifies that executeLanguageQuery's catch block is triggered
     * when a SQLException occurs, and the method returns null.
     */
    @Test
    void testRetrieveLanguageSpeakers_Exception() throws Exception {
        when(mockConnection.prepareStatement(any(String.class)))
                .thenThrow(new SQLException("DB error"));

        ArrayList<CountryLanguage> languages = report.retrieveLanguageSpeakers();

        assertNull(languages);
    }

    /**
     * Tests that printLanguageReport handles null correctly.
     */
    @Test
    void testPrintLanguageReport_Null() {
        report.printLanguageReport(null);
    }

    /**
     * Tests that printLanguageReport handles empty list.
     */
    @Test
    void testPrintLanguageReport_Empty() {
        report.printLanguageReport(new ArrayList<>());
    }

    /**
     * Tests that printLanguageReport prints correctly for a valid language.
     */
    @Test
    void testPrintLanguageReport_ValidLanguage() {
        CountryLanguage l1 = new CountryLanguage();
        l1.Language = "Chinese";
        l1.NumberOfSpeakers = 1140000000L;
        l1.WorldPercentage = 13.9;

        CountryLanguage l2 = new CountryLanguage();
        l2.Language = "Hindi";
        l2.NumberOfSpeakers = 609000000L;
        l2.WorldPercentage = 7.4;

        ArrayList<CountryLanguage> languages = new ArrayList<>();
        languages.add(l1);
        languages.add(l2);
        languages.add(null);

        report.printLanguageReport(languages);
    }
}

