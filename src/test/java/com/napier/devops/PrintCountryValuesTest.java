package com.napier.devops;

import com.napier.sem.PrintCountryValues;
import com.napier.sem.Country;
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
 * Unit tests for the {@link PrintCountryValues} class.
 * This class uses Mockito to simulate database interactions
 * and verify behavior of methods retrieving country data.
 */
class PrintCountryValuesTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private PrintCountryValues report;

    /**
     * Sets up the mock database connection and initializes the {@link PrintCountryValues} instance
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

        report = new PrintCountryValues(mockConnection);
    }

    /**
     * Verifies that details of an expected country are returned correctly.
     */
    @Test
    void testGetCountriesByPopulation_Valid() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("Code")).thenReturn("JPN");
        when(mockResultSet.getString("Name")).thenReturn("Japan");
        when(mockResultSet.getString("Continent")).thenReturn("Asia");
        when(mockResultSet.getString("Region")).thenReturn("Eastern Asia");
        when(mockResultSet.getInt("Population")).thenReturn(125800000);
        when(mockResultSet.getString("Capital")).thenReturn("Tokyo");

        ArrayList<Country> countries = report.getCountriesByPopulation();

        assertNotNull(countries);
        assertEquals(1, countries.size());
        Country c = countries.get(0);
        assertEquals("JPN", c.code);
        assertEquals("Japan", c.name);
        assertEquals("Asia", c.continent);
        assertEquals("Eastern Asia", c.region);
        assertEquals(125800000, c.population);
        assertEquals("Tokyo", c.capital);
    }

    /**
     * Verifies that executeCountryQuery's catch block is triggered
     * when a SQLException occurs, returning null.
     */
    @Test
    void testGetCountriesByPopulation_Exception() throws Exception {
        when(mockConnection.prepareStatement(any(String.class))).thenThrow(new SQLException("DB error"));

        ArrayList<Country> countries = report.getCountriesByPopulation();

        assertNull(countries);
    }

    /**
     * Verifies that a valid continent returns expected countries.
     */
    @Test
    void testGetCountriesByContinent_Valid() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("Code")).thenReturn("CHN");
        when(mockResultSet.getString("Name")).thenReturn("China");
        when(mockResultSet.getString("Continent")).thenReturn("Asia");
        when(mockResultSet.getString("Region")).thenReturn("Eastern Asia");
        when(mockResultSet.getInt("Population")).thenReturn(1400000000);
        when(mockResultSet.getString("Capital")).thenReturn("Beijing");

        ArrayList<Country> countries = report.getCountriesByContinent("Asia");

        assertNotNull(countries);
        assertEquals(1, countries.size());
        Country c = countries.get(0);
        assertEquals("CHN", c.code);
        assertEquals("China", c.name);
        assertEquals("Asia", c.continent);
        assertEquals("Eastern Asia", c.region);
        assertEquals(1400000000, c.population);
        assertEquals("Beijing", c.capital);

        verify(mockPreparedStatement).setString(1, "Asia");
    }

    /**
     * Verifies that an invalid continent returns an empty list.
     */
    @Test
    void testGetCountriesByContinent_Invalid() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        ArrayList<Country> countries = report.getCountriesByContinent("Atlantis");

        assertNotNull(countries);
        assertEquals(0, countries.size());
        verify(mockPreparedStatement).setString(1, "Atlantis");
    }

    /**
     * Verifies that passing null continent returns an empty list.
     */
    @Test
    void testGetCountriesByContinent_Null() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        ArrayList<Country> countries = report.getCountriesByContinent(null);

        assertNotNull(countries);
        assertEquals(0, countries.size());
        verify(mockPreparedStatement).setString(1, null);
    }

    /**
     * Verifies that a valid region returns expected countries.
     */
    @Test
    void testGetCountriesByRegion_Valid() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("Code")).thenReturn("DEU");
        when(mockResultSet.getString("Name")).thenReturn("Germany");
        when(mockResultSet.getString("Continent")).thenReturn("Europe");
        when(mockResultSet.getString("Region")).thenReturn("Western Europe");
        when(mockResultSet.getInt("Population")).thenReturn(83000000);
        when(mockResultSet.getString("Capital")).thenReturn("Berlin");

        ArrayList<Country> countries = report.getCountriesByRegion("Western Europe");

        assertNotNull(countries);
        assertEquals(1, countries.size());
        Country c = countries.get(0);
        assertEquals("DEU", c.code);
        assertEquals("Germany", c.name);
        assertEquals("Europe", c.continent);
        assertEquals("Western Europe", c.region);
        assertEquals(83000000, c.population);
        assertEquals("Berlin", c.capital);

        verify(mockPreparedStatement).setString(1, "Western Europe");
    }

    /**
     * Verifies that invalid region returns empty list.
     */
    @Test
    void testGetCountriesByRegion_Invalid() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        ArrayList<Country> countries = report.getCountriesByRegion("InvalidRegion");

        assertNotNull(countries);
        assertEquals(0, countries.size());
        verify(mockPreparedStatement).setString(1, "InvalidRegion");
    }

    /**
     * Verifies that null region returns empty list.
     */
    @Test
    void testGetCountriesByRegion_Null() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        ArrayList<Country> countries = report.getCountriesByRegion(null);

        assertNotNull(countries);
        assertEquals(0, countries.size());
        verify(mockPreparedStatement).setString(1, null);
    }

    /** Test printCountries handles null correctly */
    @Test
    void testPrintCountries_Null() {
        report.printCountries(null);
    }

    /** Test printCountries handles empty list correctly */
    @Test
    void testPrintCountries_Empty() {
        report.printCountries(new ArrayList<>());
    }

    /**
     * Verifies that topNCountriesByRegion correctly binds parameters and returns data.
     * This test implicitly tests the 'topNCountries' helper method.
     */
    @Test
    void testTopNCountriesByRegion_Valid() throws Exception {
        // Arrange: Simulate one country being found
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("Code")).thenReturn("JAM");
        when(mockResultSet.getString("Name")).thenReturn("Jamaica");
        when(mockResultSet.getString("Continent")).thenReturn("North America");
        when(mockResultSet.getString("Region")).thenReturn("Caribbean");
        when(mockResultSet.getInt("Population")).thenReturn(2700000);
        when(mockResultSet.getString("Capital")).thenReturn("Kingston");

        // Act: Call the method
        ArrayList<Country> countries = report.topNCountriesByRegion("Caribbean", 5);

        // Assert: Check the results
        assertNotNull(countries);
        assertEquals(1, countries.size());
        Country c = countries.get(0);
        assertEquals("JAM", c.code);
        assertEquals("Jamaica", c.name);
        assertEquals("Caribbean", c.region);
        assertEquals(2700000, c.population);

        // Verify: Check that the correct parameters were bound
        verify(mockPreparedStatement).setString(1, "Caribbean");
        verify(mockPreparedStatement).setInt(2, 5);
    }

    /**
     * Verifies that topNCountriesByRegion returns an empty list for a region with no countries.
     */
    @Test
    void testTopNCountriesByRegion_Empty() throws Exception {
        // Arrange: Simulate no countries being found
        when(mockResultSet.next()).thenReturn(false);

        // Act
        ArrayList<Country> countries = report.topNCountriesByRegion("EmptyRegion", 5);

        // Assert
        assertNotNull(countries);
        assertEquals(0, countries.size());

        // Verify: Check parameters were still bound
        verify(mockPreparedStatement).setString(1, "EmptyRegion");
        verify(mockPreparedStatement).setInt(2, 5);
    }

    /**
     * Verifies that topNCountries helper method returns null when a SQLException occurs.
     */
    @Test
    void testTopNCountriesByRegion_Exception() throws Exception {
        // Arrange: Simulate a database error
        when(mockConnection.prepareStatement(any(String.class))).thenThrow(new SQLException("DB error"));

        // Act
        ArrayList<Country> countries = report.topNCountriesByRegion("Caribbean", 5);

        // Assert
        assertNull(countries);
    }

    /**
     * Verifies that topNCountriesByContinent correctly binds parameters and returns data.
     * This test also implicitly tests the 'topNCountries' helper method.
     */
    @Test
    void testTopNCountriesByContinent_Valid() throws Exception {
        // Arrange: Simulate one country being found
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("Code")).thenReturn("BRA");
        when(mockResultSet.getString("Name")).thenReturn("Brazil");
        when(mockResultSet.getString("Continent")).thenReturn("South America");
        when(mockResultSet.getString("Region")).thenReturn("South America");
        when(mockResultSet.getInt("Population")).thenReturn(210000000);
        when(mockResultSet.getString("Capital")).thenReturn("Brasília");

        // Act
        ArrayList<Country> countries = report.topNCountriesByContinent("South America", 3);

        // Assert
        assertNotNull(countries);
        assertEquals(1, countries.size());
        Country c = countries.get(0);
        assertEquals("BRA", c.code);
        assertEquals("Brazil", c.name);
        assertEquals("South America", c.continent);

        // Verify
        verify(mockPreparedStatement).setString(1, "South America");
        verify(mockPreparedStatement).setInt(2, 3);
    }

    /**
     * Verifies that topNCountriesInTheWorld correctly binds its parameters and returns data.
     */
    @Test
    void testTopNCountriesInTheWorld_Valid() throws Exception {
        // Arrange: Simulate one country being found
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("Code")).thenReturn("CHN");
        when(mockResultSet.getString("Name")).thenReturn("China");
        when(mockResultSet.getString("Continent")).thenReturn("Asia");
        when(mockResultSet.getString("Region")).thenReturn("Eastern Asia");
        when(mockResultSet.getInt("Population")).thenReturn(1400000000);
        when(mockResultSet.getString("Capital")).thenReturn("Beijing");

        // Act
        ArrayList<Country> countries = report.topNCountriesInTheWorld(10);

        // Assert
        assertNotNull(countries);
        assertEquals(1, countries.size());
        Country c = countries.get(0);
        assertEquals("CHN", c.code);
        assertEquals("China", c.name);

        // Verify: Check that the *single integer limit* was bound
        verify(mockPreparedStatement).setInt(1, 10);
    }

    /**
     * Verifies that topNCountriesInTheWorld returns an empty list when no countries are found.
     */
    @Test
    void testTopNCountriesInTheWorld_Empty() throws Exception {
        // Arrange: Simulate no countries being found
        when(mockResultSet.next()).thenReturn(false);

        // Act
        ArrayList<Country> countries = report.topNCountriesInTheWorld(10);

        // Assert
        assertNotNull(countries);
        assertEquals(0, countries.size());

        // Verify
        verify(mockPreparedStatement).setInt(1, 10);
    }

    /**
     * Verifies that topNCountriesInTheWorld returns null when a SQLException occurs.
     */
    @Test
    void testTopNCountriesInTheWorld_Exception() throws Exception {
        // Arrange: Simulate a database error during query execution
        when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException("Query failed"));

        // Act
        ArrayList<Country> countries = report.topNCountriesInTheWorld(10);

        // Assert
        assertNull(countries);
    }

    /** Test printCountries prints correctly for valid countries */
    @Test
    void testPrintCountries_Valid() {
        Country c1 = new Country();
        c1.code = "FRA";
        c1.name = "France";
        c1.continent = "Europe";
        c1.region = "Western Europe";
        c1.population = 67000000;
        c1.capital = "Paris";

        Country c2 = new Country();
        c2.code = "ESP";
        c2.name = "Spain";
        c2.continent = "Europe";
        c2.region = "Southern Europe";
        c2.population = 47000000;
        c2.capital = "Madrid";

        ArrayList<Country> countries = new ArrayList<>();
        countries.add(c1);
        countries.add(c2);
        countries.add(null);

        report.printCountries(countries);
    }
}



