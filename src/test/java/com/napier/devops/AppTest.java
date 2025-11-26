package com.napier.devops;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.napier.sem.App;
import com.napier.sem.CityReport;
import com.napier.sem.CapitalCityReport;
import com.napier.sem.CountryReport;
import com.napier.sem.LanguageReport;
import com.napier.sem.UrbanReport;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link App} class using Mockito.
 */
class AppTest {

    private App app;
    private CityReport mockCityReport;
    private CapitalCityReport mockCapitalReport;
    private CountryReport mockCountryReport;
    private LanguageReport mockLanguageReport;
    private UrbanReport mockUrbanReport;
    private Connection mockConnection;

    /**
     * Set up mocks before each test.
     */
    @BeforeEach
    void setUp() throws SQLException {
        app = new App();

        // Create mocks
        mockCityReport = mock(CityReport.class);
        mockCapitalReport = mock(CapitalCityReport.class);
        mockCountryReport = mock(CountryReport.class);
        mockLanguageReport = mock(LanguageReport.class);
        mockUrbanReport = mock(UrbanReport.class);
        mockConnection = mock(Connection.class);

        // Mock CityReport methods
        when(mockCityReport.printAllCities()).thenReturn(new ArrayList<>());
        when(mockCityReport.printCitiesByContinent(anyString())).thenReturn(new ArrayList<>());
        when(mockCityReport.printCitiesByRegion(anyString())).thenReturn(new ArrayList<>());
        when(mockCityReport.printCitiesByDistrict(anyString())).thenReturn(new ArrayList<>());

        // Mock CapitalCityReport methods
        when(mockCapitalReport.getAllCapitalCities()).thenReturn(new ArrayList<>());
        when(mockCapitalReport.getCapitalCitiesByContinent(anyString())).thenReturn(new ArrayList<>());
        when(mockCapitalReport.getCapitalCitiesByRegion(anyString())).thenReturn(new ArrayList<>());

        // Mock CountryReport methods
        when(mockCountryReport.getCountriesByPopulation()).thenReturn(new ArrayList<>());
        when(mockCountryReport.getCountriesByContinent(anyString())).thenReturn(new ArrayList<>());
        when(mockCountryReport.getCountriesByRegion(anyString())).thenReturn(new ArrayList<>());

        // Mock LanguageReport query
        when(mockLanguageReport.retrieveLanguageSpeakers()).thenReturn(new ArrayList<>());

        // Assign mock connection
        app.con = mockConnection;
    }

    /**
     * Tests that runReports calls all reports including UrbanReport.
     */
    @Test
    void testRunReports() {
        app.runReports(
                mockCityReport,
                mockCapitalReport,
                mockCountryReport,
                mockLanguageReport,
                mockUrbanReport
        );

        // --- Verify LanguageReport calls ---
        verify(mockLanguageReport).retrieveLanguageSpeakers();
        verify(mockLanguageReport).printLanguageReport(any(ArrayList.class));

        // --- Verify CityReport calls ---
        verify(mockCityReport).printAllCities();
        verify(mockCityReport).printCitiesByContinent("Asia");
        verify(mockCityReport).printCitiesByRegion("South America");
        verify(mockCityReport).printCitiesByDistrict("Oran");

        // --- Verify CapitalCityReport calls ---
        verify(mockCapitalReport).getAllCapitalCities();
        verify(mockCapitalReport).getCapitalCitiesByContinent("Asia");
        verify(mockCapitalReport).getCapitalCitiesByRegion("North America");

        // --- Verify CountryReport calls ---
        verify(mockCountryReport).getCountriesByPopulation();
        verify(mockCountryReport).getCountriesByContinent("Europe");
        verify(mockCountryReport).getCountriesByRegion("North America");

        // --- Verify UrbanReport is executed ---
        verify(mockUrbanReport).generateReportLists(mockConnection);
    }

    /**
     * Tests that disconnect closes the connection.
     */
    @Test
    void testDisconnectClosesConnection() throws SQLException {
        app.disconnect();
        verify(mockConnection).close();
    }

    /**
     * Tests safe disconnect when con is null.
     */
    @Test
    void testDisconnectWhenConnectionIsNull() {
        app.con = null;
        app.disconnect(); // Should not throw
    }

    @Test
    void testConnectWithException() throws Exception {
        // Can't test System.exit safely, so left empty intentionally
    }
}
