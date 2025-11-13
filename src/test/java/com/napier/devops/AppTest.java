package com.napier.devops;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.napier.sem.App;
import com.napier.sem.City;
import com.napier.sem.CityReport;
import com.napier.sem.CountryReport;
import com.napier.sem.CapitalCityReport;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link App} class using Mockito.
 * Tests connection handling, disconnection, and running reports.
 */
class AppTest {

    private App app;
    private CityReport mockCityReport;
    private CapitalCityReport mockCapitalReport;
    private CountryReport mockCountryReport;
    private Connection mockConnection;

    /**
     * Set up mocks for reports and connection before each test.
     */
    @BeforeEach
    void setUp() throws SQLException {
        app = new App();

        // Create mocks
        mockCityReport = mock(CityReport.class);
        mockCapitalReport = mock(CapitalCityReport.class);
        mockCountryReport = mock(CountryReport.class);
        mockConnection = mock(Connection.class);

        // Mock report methods to return empty lists
        when(mockCityReport.printAllCities()).thenReturn(new ArrayList<>());
        when(mockCityReport.printCitiesByContinent(anyString())).thenReturn(new ArrayList<>());
        when(mockCityReport.printCitiesByRegion(anyString())).thenReturn(new ArrayList<>());
        when(mockCityReport.printCitiesByDistrict(anyString())).thenReturn(new ArrayList<>());

        when(mockCapitalReport.getAllCapitalCities()).thenReturn(new ArrayList<>());
        when(mockCapitalReport.getCapitalCitiesByContinent(anyString())).thenReturn(new ArrayList<>());
        when(mockCapitalReport.getCapitalCitiesByRegion(anyString())).thenReturn(new ArrayList<>());

        when(mockCountryReport.getCountriesByPopulation()).thenReturn(new ArrayList<>());
        when(mockCountryReport.getCountriesByContinent(anyString())).thenReturn(new ArrayList<>());
        when(mockCountryReport.getCountriesByRegion(anyString())).thenReturn(new ArrayList<>());

        // Assign mock connection
        app.con = mockConnection;
    }

    /**
     * Tests that {@link App#runReports(CityReport, CapitalCityReport, CountryReport)}
     * calls all report methods as expected.
     */
    @Test
    void testRunReports() {
        app.runReports(mockCityReport, mockCapitalReport, mockCountryReport);

        // Verify that each report method was called
        verify(mockCityReport).printAllCities();
        verify(mockCityReport).printCitiesByContinent("Asia");
        verify(mockCityReport).printCitiesByRegion("South America");
        verify(mockCityReport).printCitiesByDistrict("Oran");

        verify(mockCapitalReport).getAllCapitalCities();
        verify(mockCapitalReport).getCapitalCitiesByContinent("Asia");
        verify(mockCapitalReport).getCapitalCitiesByRegion("North America");

        verify(mockCountryReport).getCountriesByPopulation();
        verify(mockCountryReport).getCountriesByContinent("Europe");
        verify(mockCountryReport).getCountriesByRegion("North America");
    }

    /**
     * Tests that {@link App#disconnect()} closes the connection when it is active.
     */
    @Test
    void testDisconnectClosesConnection() throws SQLException {
        app.disconnect();
        verify(mockConnection).close();
    }

    /**
     * Tests that {@link App#disconnect()} does not throw an exception when
     * the connection is null.
     */
    @Test
    void testDisconnectWhenConnectionIsNull() {
        app.con = null;
        app.disconnect(); // Should not throw exception
    }

    /**
     * Example of testing connection failure behavior. Full coverage of
     * {@link App#connect(String, int)} is tricky due to System.exit calls.
     */
    @Test
    void testConnectWithException() throws Exception {
        App app2 = spy(new App());
        // Cannot directly test System.exit(-1), so this is a placeholder
        // for connection exception handling coverage
    }
}
