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
    void testGetPopulationOfWorld_ExceptionHandled() throws Exception {
        when(mockConnection.createStatement()).thenThrow(new RuntimeException("DB error"));

        mockReport.getPopulationOfWorld(mockConnection);

        assertEquals(0.0, mockReport.population, 0.001);
    }

    @Test
    void testGetUrbanPopulation_ExceptionHandled() throws Exception {
        mockReport.population = 7000000000.0;
        when(mockStatement.executeQuery("SELECT SUM(population) FROM city")).thenThrow(new RuntimeException("Query failed"));

        mockReport.getUrbanPopulation(mockConnection);

        assertEquals(0.0, mockReport.urbanPopulation, 0.001);
        assertEquals(0.0, mockReport.percentage, 0.001);
    }
}
