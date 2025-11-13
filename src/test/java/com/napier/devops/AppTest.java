package com.napier.devops;

import com.napier.sem.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
/**
 * Unit tests for the App class using mocks.
 * Verifies that runReports interacts correctly with report classes
 * without needing a real database connection.
 */
class AppTest {

    App app;
    CityReport mockCityReport;
    CapitalCityReport mockCapitalReport;
    CountryReport mockCountryReport;

    /**
     * Sets up the mock database connection and initializes the {@link App} instance
     * before each test. Mocks the behavior of {@link Connection}, {@link PreparedStatement},
     * and {@link ResultSet} to simulate database interactions.
     *
     * @throws Exception if mocking fails
     */
    @BeforeEach
    void setUp() {
        app = new App();
        mockCityReport = mock(CityReport.class);
        mockCapitalReport = mock(CapitalCityReport.class);
        mockCountryReport = mock(CountryReport.class);
    }

    /**
     * Ensures runReports calls methods on mocked CityReport,
     * CapitalCityReport, and CountryReport correctly.
     */
    @Test
    void testRunReports_callsAllMethods() {
        // Prepare mock data
        ArrayList<City> emptyCityList = new ArrayList<>();
        ArrayList<Country> emptyCountryList = new ArrayList<>();

        // Stub methods to return empty lists
        when(mockCityReport.printAllCities()).thenReturn(emptyCityList);
        when(mockCityReport.printCitiesByContinent(anyString())).thenReturn(emptyCityList);
        when(mockCityReport.printCitiesByRegion(anyString())).thenReturn(emptyCityList);
        when(mockCityReport.printCitiesByDistrict(anyString())).thenReturn(emptyCityList);

        when(mockCapitalReport.getAllCapitalCities()).thenReturn(emptyCityList);
        when(mockCapitalReport.getCapitalCitiesByContinent(anyString())).thenReturn(emptyCityList);
        when(mockCapitalReport.getCapitalCitiesByRegion(anyString())).thenReturn(emptyCityList);

        when(mockCountryReport.getCountriesByPopulation()).thenReturn(emptyCountryList);
        when(mockCountryReport.getCountriesByContinent(anyString())).thenReturn(emptyCountryList);
        when(mockCountryReport.getCountriesByRegion(anyString())).thenReturn(emptyCountryList);

        // Run the method under test
        app.runReports(mockCityReport, mockCapitalReport, mockCountryReport);

        // Verify that the expected methods were called
        verify(mockCityReport).printAllCities();
        verify(mockCityReport).printCitiesByContinent("Asia");
        verify(mockCityReport).printCitiesByRegion("South America");
        verify(mockCityReport).printCitiesByDistrict("Oran");

        verify(mockCityReport, times(4)).printCities(emptyCityList);

        verify(mockCapitalReport).getAllCapitalCities();
        verify(mockCapitalReport).getCapitalCitiesByContinent("Asia");
        verify(mockCapitalReport).getCapitalCitiesByRegion("North America");

        verify(mockCapitalReport, times(3)).printCapitalCities(emptyCityList);

        verify(mockCountryReport).getCountriesByPopulation();
        verify(mockCountryReport).getCountriesByContinent("Europe");
        verify(mockCountryReport).getCountriesByRegion("North America");

        verify(mockCountryReport, times(3)).printCountries(emptyCountryList);
    }
}
