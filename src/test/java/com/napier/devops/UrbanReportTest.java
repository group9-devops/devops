package com.napier.devops;

import com.napier.sem.UrbanReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UrbanReportTest {

    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;
    private UrbanReport mockReport;
    public String arg;

    @BeforeEach
    void setUp() throws Exception {
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        mockReport = new UrbanReport();

        when(mockConnection.createStatement()).thenReturn(mockStatement);
    }

    @Test
    void testGetPopulationOfWorld() throws Exception {
        when(mockStatement.executeQuery("SELECT SUM(population) FROM country")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getDouble(1)).thenReturn(6078749450.0);

        mockReport.getPopulationOfWorld(mockConnection);

        assertEquals(6078749450.0, mockReport.population, 0.001);
    }

    @Test
    void testGetUrbanPopulation() throws Exception {
        mockReport.population = 6078749450.0;
        when(mockStatement.executeQuery("SELECT SUM(population) FROM city")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getDouble(1)).thenReturn(1429559884.0);

        mockReport.getUrbanPopulation(mockConnection);

        assertEquals(1429559884.0, mockReport.urbanPopulation, 0.001);
        assertEquals((1429559884.0 / 6078749450.0) * 100, mockReport.percentage, 0.001);
    }

    @Test
    void testGetPopulationOfRegion() throws Exception {
        when(mockStatement.executeQuery("SELECT SUM(population) FROM country WHERE Region = 'British Islands'")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getDouble(1)).thenReturn(63398500.0);

        mockReport.getPopulationOfRegion(mockConnection, "British Islands");

        verify(mockStatement).executeQuery("SELECT SUM(population) FROM country WHERE Region = 'British Islands'");
    }

    @Test
    void testGetUrbanPopulationOfRegion() throws Exception {
        when(mockStatement.executeQuery("SELECT SUM(city.population) FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Region = 'British Islands'")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getDouble(1)).thenReturn(19401506.0);

        mockReport.getUrbanPopulationOfRegion(mockConnection, "British Islands");

        verify(mockStatement).executeQuery("SELECT SUM(city.population) FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Region = 'British Islands'");
    }

    @Test
    void testGetPopulationOfCountry() throws Exception {
        when(mockStatement.executeQuery("SELECT population FROM country WHERE Name = 'Afghanistan'")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getDouble(1)).thenReturn(22720000.0);

        mockReport.getPopulationOfCountry(mockConnection, "Afghanistan");

        verify(mockStatement).executeQuery("SELECT population FROM country WHERE Name = 'Afghanistan'");
    }

    @Test
    void testGetUrbanPopulationOfCountry() throws Exception {
        when(mockStatement.executeQuery("SELECT SUM(city.population) FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Name = 'Afghanistan'")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getDouble(1)).thenReturn(2332100.0);

        mockReport.getUrbanPopulationOfCountry(mockConnection, "Afghanistan");

        verify(mockStatement).executeQuery("SELECT SUM(city.population) FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Name = 'Afghanistan'");
    }

    @Test
    void testGetPopulationOfContinent() throws Exception {
        when(mockStatement.executeQuery("SELECT SUM(population) FROM country WHERE Continent = 'Asia'")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getDouble(1)).thenReturn(3705025700.0);

        mockReport.getPopulationOfContinent(mockConnection, "Asia");

        verify(mockStatement).executeQuery("SELECT SUM(population) FROM country WHERE Continent = 'Asia'");
    }

    @Test
    void testGetUrbanPopulationOfContinent() throws Exception {
        when(mockStatement.executeQuery("SELECT SUM(city.population) FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Continent = 'Asia'")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getDouble(1)).thenReturn(697604103.0);

        mockReport.getUrbanPopulationOfContinent(mockConnection, "Asia");

        verify(mockStatement).executeQuery("SELECT SUM(city.population) FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Continent = 'Asia'");
    }

    @Test
    void testGetPopulationOfCity() throws Exception {
        when(mockStatement.executeQuery("SELECT population FROM city WHERE Name = 'Liverpool'")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getDouble(1)).thenReturn(461000.0);

        mockReport.getPopulationOfCity(mockConnection, "Liverpool");

        verify(mockStatement).executeQuery("SELECT population FROM city WHERE Name = 'Liverpool'");
    }

    @Test
    void testGetPopulationOfWorld_ExceptionHandled() throws Exception {
        when(mockConnection.createStatement()).thenThrow(new RuntimeException("DB error"));

        mockReport.getPopulationOfWorld(mockConnection);

        assertEquals(0.0, mockReport.population, 0.001);
    }

    @Test
    void testGetUrbanPopulation_ExceptionHandled() throws Exception {
        mockReport.population = 6078749450.0;
        when(mockStatement.executeQuery("SELECT SUM(population) FROM city")).thenThrow(new RuntimeException("Query failed"));

        mockReport.getUrbanPopulation(mockConnection);

        assertEquals(0.0, mockReport.urbanPopulation, 0.001);
        assertEquals(0.0, mockReport.percentage, 0.001);
    }

    @Test
    void testGetPopulationOfRegion_ExceptionHandled() throws Exception {
        when(mockConnection.createStatement()).thenThrow(new RuntimeException("DB error"));

        mockReport.getPopulationOfRegion(mockConnection, "British Islands");

        assertEquals(0.0, mockReport.urbanPopulation, 0.001);
    }

    @Test
    void testGetUrbanPopulationOfRegion_ExceptionHandled() throws Exception {
        when(mockConnection.createStatement()).thenThrow(new RuntimeException("DB error"));

        mockReport.getUrbanPopulationOfRegion(mockConnection, "British Islands");

        assertEquals(0.0, mockReport.urbanPopulation, 0.001);
    }

    @Test
    void testGetPopulationOfContinent_ExceptionHandled() throws Exception {
        when(mockConnection.createStatement()).thenThrow(new RuntimeException("DB error"));

        mockReport.getPopulationOfContinent(mockConnection, "Asia");

        assertEquals(0.0, mockReport.urbanPopulation, 0.001);
    }

    @Test
    void testGetUrbanPopulationOfContinent_ExceptionHandled() throws Exception {
        when(mockConnection.createStatement()).thenThrow(new RuntimeException("DB error"));

        mockReport.getUrbanPopulationOfContinent(mockConnection, "Asia");

        assertEquals(0.0, mockReport.urbanPopulation, 0.001);
    }

    @Test
    void testGetPopulationOfCountry_ExceptionHandled() throws Exception {
        when(mockConnection.createStatement()).thenThrow(new RuntimeException("DB error"));

        mockReport.getPopulationOfCountry(mockConnection, "Afghanistan");

        assertEquals(0.0, mockReport.urbanPopulation, 0.001);
    }

    @Test
    void testGetUrbanPopulationOfCountry_ExceptionHandled() throws Exception {
        when(mockConnection.createStatement()).thenThrow(new RuntimeException("DB error"));

        mockReport.getUrbanPopulationOfCountry(mockConnection, "Afghanistan");

        assertEquals(0.0, mockReport.urbanPopulation, 0.001);
    }

    @Test
    void testGetPopulationOfCity_ExceptionHandled() throws Exception {
        when(mockConnection.createStatement()).thenThrow(new RuntimeException("DB error"));

        mockReport.getPopulationOfCity(mockConnection, "Liverpool");

        assertEquals(0.0, mockReport.urbanPopulation, 0.001);
    }
}
