package com.napier.devops;

import com.napier.sem.UrbanReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link UrbanReport}.
 * <p>
 * These tests use Mockito to mock database interactions via PreparedStatements
 * and ResultSets. They verify population queries and report generation logic.
 */
class UrbanReportTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private UrbanReport report;

    @BeforeEach
    void setUp() throws Exception {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        report = new UrbanReport();
    }

    /**
     * Tests that {@link UrbanReport#getPopulationOfWorld(Connection)}
     * correctly retrieves the world's population.
     */
    @Test
    void testGetPopulationOfWorld() throws Exception {
        when(mockConnection.prepareStatement("SELECT SUM(population) FROM country")).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getDouble(1)).thenReturn(6078749450.0);

        report.getPopulationOfWorld(mockConnection);

        assertEquals(6078749450.0, report.population, 0.001);
    }

    /**
     * Tests that {@link UrbanReport#getUrbanPopulation(Connection)}
     * correctly retrieves the urban population of the world.
     */
    @Test
    void testGetUrbanPopulation() throws Exception {
        report.population = 6078749450.0;

        when(mockConnection.prepareStatement("SELECT SUM(population) FROM city")).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getDouble(1)).thenReturn(1429559884.0);

        report.getUrbanPopulation(mockConnection);

        assertEquals(1429559884.0, report.urbanPopulation, 0.001);
    }

    /**
     * Tests that {@link UrbanReport#getPopulationOfRegion(Connection, String)}
     * correctly retrieves a region's population.
     */
    @Test
    void testGetPopulationOfRegion() throws Exception {
        String sql = "SELECT SUM(population) FROM country WHERE Region = ?";
        when(mockConnection.prepareStatement(sql)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getDouble(1)).thenReturn(63398500.0);

        report.getPopulationOfRegion(mockConnection, "British Islands");

        verify(mockPreparedStatement).setString(1, "British Islands");
        assertEquals(63398500.0, report.population, 0.001);
    }

    /**
     * Tests that {@link UrbanReport#getUrbanPopulationOfRegion(Connection, String)}
     * correctly retrieves a region's urban population.
     */
    @Test
    void testGetUrbanPopulationOfRegion() throws Exception {
        String sql = "SELECT SUM(city.population) FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Region = ?";
        when(mockConnection.prepareStatement(sql)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getDouble(1)).thenReturn(19401506.0);

        report.getUrbanPopulationOfRegion(mockConnection, "British Islands");

        verify(mockPreparedStatement).setString(1, "British Islands");
        assertEquals(19401506.0, report.urbanPopulation, 0.001);
    }

    /**
     * Tests that {@link UrbanReport#getPopulationOfCountry(Connection, String)}
     * correctly retrieves a country's population.
     */
    @Test
    void testGetPopulationOfCountry() throws Exception {
        String sql = "SELECT population FROM country WHERE Name = ?";
        when(mockConnection.prepareStatement(sql)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getDouble(1)).thenReturn(22720000.0);

        report.getPopulationOfCountry(mockConnection, "Afghanistan");

        verify(mockPreparedStatement).setString(1, "Afghanistan");
        assertEquals(22720000.0, report.population, 0.001);
    }

    /**
     * Tests that {@link UrbanReport#getUrbanPopulationOfCountry(Connection, String)}
     * correctly retrieves a country's urban population.
     */
    @Test
    void testGetUrbanPopulationOfCountry() throws Exception {
        String sql = "SELECT SUM(city.population) FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Name = ?";
        when(mockConnection.prepareStatement(sql)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getDouble(1)).thenReturn(2332100.0);

        report.getUrbanPopulationOfCountry(mockConnection, "Afghanistan");

        verify(mockPreparedStatement).setString(1, "Afghanistan");
        assertEquals(2332100.0, report.urbanPopulation, 0.001);
    }



    /**
     * Tests that {@link UrbanReport#generateReportLists(Connection)}
     * generates lists of regions, countries, and cities without errors.
     */
    @Test
    void testGenerateReportLists() throws Exception {
        // Mock continent population
        String sqlContinent = "SELECT SUM(population) FROM country WHERE Continent = ?";
        when(mockConnection.prepareStatement(sqlContinent)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getDouble(1)).thenReturn(100.0);

        report.generateReportLists(mockConnection);

        verify(mockPreparedStatement, atLeast(1)).executeQuery();
    }

    /**
     * Tests exception handling for population queries: ensures that when
     * a SQLException occurs, the population is set to 0.
     */
    @Test
    void testPopulationQuery_ExceptionHandled() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("DB error"));

        report.getPopulationOfWorld(mockConnection);
        assertEquals(0.0, report.population, 0.001);

        report.getUrbanPopulation(mockConnection);
        assertEquals(0.0, report.urbanPopulation, 0.001);
    }
}
