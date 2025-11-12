package com.napier.devops;

import com.napier.sem.CapitalCityReport;
import com.napier.sem.City;
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
 * Unit tests for the {@link CapitalCityReport} class.
 * This test class uses Mockito to mock database interactions and verify
 * the behavior of methods that retrieve capital city data from a database.
 *
 */

class CapitalCityReportTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private CapitalCityReport report;



    /**
     * Sets up the mock database connection and initializes the {@link CapitalCityReport} instance
     * before each test. Mocks the behavior of {@link Connection}, {@link PreparedStatement},
     * and {@link ResultSet} to simulate database interactions.
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

        report = new CapitalCityReport(mockConnection);
    }

    /**
     * Verifies that details of an expected capital city are return.
     *
     * @throws Exception if a database error occurs during the test
     */
    @Test
    void testGetAllCapitalCities_Valid() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("CapitalCity")).thenReturn("Tokyo");
        when(mockResultSet.getString("Country")).thenReturn("Japan");
        when(mockResultSet.getInt("Population")).thenReturn(13929286);

        ArrayList<City> cities = report.getAllCapitalCities();

        assertNotNull(cities);
        assertEquals(1, cities.size());
        assertEquals("Tokyo", cities.get(0).Name);
        assertEquals("Japan", cities.get(0).Country);
        assertEquals(13929286, cities.get(0).Population);
    }

    /**
     * Verifies that executeCapitalCityQuery's catch block is triggered
     * when a SQLException occurs, and the method returns null.
     */
    @Test
    void testGetAllCapitalCities_Exception() throws Exception {
        // Make the mock connection throw an exception when prepareStatement is called
        when(mockConnection.prepareStatement(any(String.class)))
                .thenThrow(new SQLException("DB error"));

        ArrayList<City> cities = report.getAllCapitalCities();

        // Catch block should be triggered and null returned
        assertNull(cities);
    }



    /**
     * Verifies that a valid continent returns the expected capital city.
     *
     * @throws Exception if a database error occurs during the test
     */

    @Test
    void testGetCapitalCitiesByContinent_Valid() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("CapitalCity")).thenReturn("Beijing");
        when(mockResultSet.getString("Country")).thenReturn("China");
        when(mockResultSet.getInt("Population")).thenReturn(69000);

        ArrayList<City> cities = report.getCapitalCitiesByContinent("Asia");

        assertNotNull(cities);
        assertEquals(1, cities.size());
        assertEquals("Beijing", cities.get(0).Name);
        assertEquals("China", cities.get(0).Country);
        assertEquals(69000, cities.get(0).Population);

        verify(mockPreparedStatement).setString(1, "Asia");
    }


    /**
     * Verifies that an invalid continent returns an empty list of capital cities.
     *
     * @throws Exception if a database error occurs during the test
     */

    @Test
    void testGetCapitalCitiesByContinent_Invalid() throws Exception {
        when(mockResultSet.next()).thenReturn(false); // no rows

        ArrayList<City> cities = report.getCapitalCitiesByContinent("China");

        assertNotNull(cities);
        assertEquals(0, cities.size());
        verify(mockPreparedStatement).setString(1, "China");
    }

    /**
     * Verifies that passing a null region does not cause an exception
     * and returns an empty list of cities.
     *
     * @throws Exception if a database error occurs during the test
     */
    @Test
    void testGetCapitalCitiesByContinent_Null() throws Exception {
        when(mockResultSet.next()).thenReturn(false); // simulate no rows

        ArrayList<City> cities = report.getCapitalCitiesByContinent(null);

        assertNotNull(cities); // should not be null
        assertEquals(0, cities.size()); // should be empty
        verify(mockPreparedStatement).setString(1, null); // parameter should still be set
    }


    /**
     * Verifies that a valid region returns the expected capital city.
     *
     * @throws Exception if a database error occurs during the test
     */

    @Test
    void testGetCapitalCitiesByRegion_Valid() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("CapitalCity")).thenReturn("Berlin");
        when(mockResultSet.getString("Country")).thenReturn("Germany");
        when(mockResultSet.getInt("Population")).thenReturn(3769000);

        ArrayList<City> cities = report.getCapitalCitiesByRegion("Western Europe");

        assertNotNull(cities);
        assertEquals(1, cities.size());
        assertEquals("Berlin", cities.get(0).Name);
        assertEquals("Germany", cities.get(0).Country);
        assertEquals(3769000, cities.get(0).Population);

        verify(mockPreparedStatement).setString(1, "Western Europe");
    }


    /**
     * Verifies that an invalid region returns an empty list of capital cities.
     *
     * @throws Exception if a database error occurs during the test
     */

    @Test
    void testGetCapitalCitiesByRegion_Invalid() throws Exception {
        when(mockResultSet.next()).thenReturn(false); // no rows

        ArrayList<City> cities = report.getCapitalCitiesByRegion("InvalidRegion");

        assertNotNull(cities);
        assertEquals(0, cities.size());
        verify(mockPreparedStatement).setString(1, "InvalidRegion");
    }


    /**
     * Verifies that passing a null region does not cause an exception
     * and returns an empty list of cities.
     *
     * @throws Exception if a database error occurs during the test
     */
    @Test
    void testGetCapitalCitiesByRegion_Null() throws Exception {
        when(mockResultSet.next()).thenReturn(false); // simulate no rows

        ArrayList<City> cities = report.getCapitalCitiesByRegion(null);

        assertNotNull(cities); // should not be null
        assertEquals(0, cities.size()); // should be empty
        verify(mockPreparedStatement).setString(1, null); // parameter should still be set
    }
    /** Test printCapitalCities handles null correctly */
    @Test
    void testPrintCapitalCities_Null() {
        report.printCapitalCities(null);
    }

    /** Test printCapitalCities handles empty list */
    @Test
    void testPrintCapitalCities_Empty() {
        report.printCapitalCities(new ArrayList<>());
    }

    /** Test printCapitalCities prints correctly for a valid city */
    @Test
    void testPrintCapitalCities_ValidCity() {
        City c1 = new City();
        c1.Name = "Paris";
        c1.Country = "France";
        c1.Population = 2148000;

        City c2 = new City();
        c2.Name = "Berlin";
        c2.Country = "Germany";
        c2.Population = 2048000;

        ArrayList<City> cities = new ArrayList<>();
        cities.add(c1); cities.add(c2); cities.add(null);

        report.printCapitalCities(cities);
    }


}





