package com.napier.devops;

import com.napier.sem.Country;
import com.napier.sem.UrbanReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UrbanReportTest {

    private Connection mockCon;
    private PreparedStatement mockStmt;
    private ResultSet mockResult;
    private UrbanReport report;

    @BeforeEach
    void setUp() throws Exception {
        mockCon = mock(Connection.class);
        mockStmt = mock(PreparedStatement.class);
        mockResult = mock(ResultSet.class);
        report = new UrbanReport(mockCon);
    }

    @Test
    void testGetUrbanPopulationWorld() throws Exception {
        when(mockCon.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockResult);
        when(mockResult.next()).thenReturn(true).thenReturn(false);
        when(mockResult.getString("Name")).thenReturn("World");
        when(mockResult.getLong("TotalPopulation")).thenReturn(8000000000L);
        when(mockResult.getLong("UrbanPopulation")).thenReturn(4000000000L);
        when(mockResult.getDouble("UrbanPercentage")).thenReturn(50.0);

        ArrayList<Country> results = report.getUrbanPopulationWorld();

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("World", results.get(0).Name);
        assertEquals(50.0, results.get(0).UrbanPercentage);
    }

    @Test
    void testExecuteUrbanQueryHandlesException() throws Exception {
        when(mockCon.prepareStatement(anyString())).thenThrow(new SQLException("DB error"));

        ArrayList<Country> results = report.getUrbanPopulationWorld();
        assertNull(results, "Should return null if SQL execution fails");
    }

    @Test
    void printUrbanPopulationTestNull() throws Exception {
        report.printUrbanPopulation(null);
    }


}
