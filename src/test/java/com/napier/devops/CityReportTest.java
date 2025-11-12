package com.napier.devops;

import com.napier.sem.City;
import com.napier.sem.CityReport;
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
 * Unit tests for the {@link CityReport} class.
 * <p>
 * This test suite verifies the behavior of CityReport methods using Mockito to
 * simulate database interactions. It tests query execution, exception handling,
 * and output formatting for various scenarios.
 */
class CityReportTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private CityReport report;

    /**
     * Sets up the mock database components before each test.
     * <p>
     * Mocks the {@link Connection}, {@link PreparedStatement}, and {@link ResultSet}
     * so that SQL queries can be tested without requiring an actual database.
     *
     * @throws Exception if mocking fails
     */
    @BeforeEach
    void setUp() throws Exception {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        report = new CityReport(mockConnection);
    }



    /**
     * Verifies that {@link CityReport#printAllCities()} correctly retrieves and maps
     * city data when valid results are returned from the database.
     *
     * @throws Exception if SQL operations fail
     */
    @Test
    void testPrintAllCities_Valid() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("CityName")).thenReturn("Tokyo");
        when(mockResultSet.getString("Country")).thenReturn("Japan");
        when(mockResultSet.getString("District")).thenReturn("Tokyo-to");
        when(mockResultSet.getInt("Population")).thenReturn(13929286);

        ArrayList<City> cities = report.printAllCities();

        assertNotNull(cities);
        assertEquals(1, cities.size());
        assertEquals("Tokyo", cities.get(0).Name);
        assertEquals("Japan", cities.get(0).Country);
        assertEquals("Tokyo-to", cities.get(0).District);
        assertEquals(13929286, cities.get(0).Population);
    }

    /**
     * Verifies that {@link CityReport#printAllCities()} handles SQL exceptions
     * correctly and returns null when an error occurs.
     *
     * @throws Exception if SQL mocking fails
     */
    @Test
    void testPrintAllCities_Exception() throws Exception {
        when(mockConnection.prepareStatement(any(String.class)))
                .thenThrow(new SQLException("DB error"));

        ArrayList<City> cities = report.printAllCities();

        assertNull(cities, "Should return null on SQL exception.");
    }



    /**
     * Verifies that {@link CityReport#printCitiesByContinent(String)} retrieves
     * the correct cities for a valid continent.
     *
     * @throws Exception if SQL operations fail
     */
    @Test
    void testPrintCitiesByContinent_Valid() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("CityName")).thenReturn("Cairo");
        when(mockResultSet.getString("Country")).thenReturn("Egypt");
        when(mockResultSet.getString("District")).thenReturn("Cairo");
        when(mockResultSet.getInt("Population")).thenReturn(9500000);

        ArrayList<City> cities = report.printCitiesByContinent("Africa");

        assertNotNull(cities);
        assertEquals(1, cities.size());
        assertEquals("Cairo", cities.get(0).Name);
        assertEquals("Egypt", cities.get(0).Country);
        assertEquals("Cairo", cities.get(0).District);
        assertEquals(9500000, cities.get(0).Population);
        verify(mockPreparedStatement).setString(1, "Africa");
    }

    /**
     * Verifies that {@link CityReport#printCitiesByContinent(String)} returns
     * an empty list when no matching continent is found.
     *
     * @throws Exception if SQL operations fail
     */
    @Test
    void testPrintCitiesByContinent_Invalid() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        ArrayList<City> cities = report.printCitiesByContinent("Atlantis");

        assertNotNull(cities);
        assertTrue(cities.isEmpty());
        verify(mockPreparedStatement).setString(1, "Atlantis");
    }

    /**
     * Verifies that {@link CityReport#printCitiesByContinent(String)} handles
     * a null parameter gracefully and returns an empty list.
     *
     * @throws Exception if SQL operations fail
     */
    @Test
    void testPrintCitiesByContinent_Null() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        ArrayList<City> cities = report.printCitiesByContinent(null);

        assertNotNull(cities);
        assertTrue(cities.isEmpty());
        verify(mockPreparedStatement).setString(1, null);
    }



    /**
     * Verifies that {@link CityReport#printCitiesByRegion(String)} retrieves
     * the correct cities for a valid region.
     *
     * @throws Exception if SQL operations fail
     */
    @Test
    void testPrintCitiesByRegion_Valid() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("CityName")).thenReturn("Berlin");
        when(mockResultSet.getString("Country")).thenReturn("Germany");
        when(mockResultSet.getString("District")).thenReturn("Berlin");
        when(mockResultSet.getInt("Population")).thenReturn(3769000);

        ArrayList<City> cities = report.printCitiesByRegion("Western Europe");

        assertNotNull(cities);
        assertEquals(1, cities.size());
        assertEquals("Berlin", cities.get(0).Name);
        assertEquals("Germany", cities.get(0).Country);
        assertEquals("Berlin", cities.get(0).District);
        assertEquals(3769000, cities.get(0).Population);
        verify(mockPreparedStatement).setString(1, "Western Europe");
    }

    /**
     * Verifies that {@link CityReport#printCitiesByRegion(String)} returns
     * an empty list when the region is invalid or not found.
     *
     * @throws Exception if SQL operations fail
     */
    @Test
    void testPrintCitiesByRegion_Invalid() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        ArrayList<City> cities = report.printCitiesByRegion("InvalidRegion");

        assertNotNull(cities);
        assertTrue(cities.isEmpty());
        verify(mockPreparedStatement).setString(1, "InvalidRegion");
    }

    /**
     * Verifies that {@link CityReport#printCitiesByRegion(String)} handles
     * a null region parameter correctly.
     *
     * @throws Exception if SQL operations fail
     */
    @Test
    void testPrintCitiesByRegion_Null() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        ArrayList<City> cities = report.printCitiesByRegion(null);

        assertNotNull(cities);
        assertTrue(cities.isEmpty());
        verify(mockPreparedStatement).setString(1, null);
    }



    /**
     * Verifies that {@link CityReport#printCitiesByDistrict(String)} retrieves
     * the correct cities for a valid district.
     *
     * @throws Exception if SQL operations fail
     */
    @Test
    void testPrintCitiesByDistrict_Valid() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("CityName")).thenReturn("Manchester");
        when(mockResultSet.getString("Country")).thenReturn("United Kingdom");
        when(mockResultSet.getString("District")).thenReturn("England");
        when(mockResultSet.getInt("Population")).thenReturn(553230);

        ArrayList<City> cities = report.printCitiesByDistrict("England");

        assertNotNull(cities);
        assertEquals(1, cities.size());
        assertEquals("Manchester", cities.get(0).Name);
        assertEquals("United Kingdom", cities.get(0).Country);
        assertEquals("England", cities.get(0).District);
        assertEquals(553230, cities.get(0).Population);
        verify(mockPreparedStatement).setString(1, "England");
    }

    /**
     * Verifies that {@link CityReport#printCitiesByDistrict(String)} returns
     * an empty list when an invalid district is provided.
     *
     * @throws Exception if SQL operations fail
     */
    @Test
    void testPrintCitiesByDistrict_Invalid() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        ArrayList<City> cities = report.printCitiesByDistrict("Nowhere");

        assertNotNull(cities);
        assertTrue(cities.isEmpty());
        verify(mockPreparedStatement).setString(1, "Nowhere");
    }

    /**
     * Verifies that {@link CityReport#printCitiesByDistrict(String)} handles
     * a null district parameter gracefully and returns an empty list.
     *
     * @throws Exception if SQL operations fail
     */
    @Test
    void testPrintCitiesByDistrict_Null() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        ArrayList<City> cities = report.printCitiesByDistrict(null);

        assertNotNull(cities);
        assertTrue(cities.isEmpty());
        verify(mockPreparedStatement).setString(1, null);
    }



    /**
     * Verifies that {@link CityReport#printCities(ArrayList)} handles
     * a null input list without throwing an exception.
     */
    @Test
    void testPrintCities_Null() {
        report.printCities(null);
    }

    /**
        * Verifies that {@link CityReport#printCities(ArrayList)} handles
    * an empty list gracefully*/

    @Test
    void testPrintCities_Empty() {
        ArrayList<City> cities = new ArrayList<City>();
        cities.add(new City());
        report.printCities(cities);
    }

    /**
     * Verifies that {@link CityReport#printCities(ArrayList)} prints valid
     * city data and safely skips null entries in the list.
     */
    @Test
    void testPrintCities_ValidList() {
        City c1 = new City();
        c1.Name = "Paris";
        c1.Country = "France";
        c1.District = "Île-de-France";
        c1.Population = 2148000;

        City c2 = new City();
        c2.Name = "Berlin";
        c2.Country = "Germany";
        c2.District = "Berlin";
        c2.Population = 3769000;

        ArrayList<City> list = new ArrayList<>();
        list.add(c1);
        list.add(c2);
        list.add(null); // test null handling inside loop

        report.printCities(list);
    }
}

