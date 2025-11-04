package com.napier.sem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PrintCountryValues {
    public void getAllCountriesByPopulationDescending(Connection con) {
        ArrayList<Country> countries = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();
            String sqlStatement = "SELECT * FROM country ORDER BY population DESC";
            ResultSet rset = stmt.executeQuery(sqlStatement);

            while (rset.next()) {
                Country country = new Country();
                country.Code = rset.getString("Code");
                country.Name = rset.getString("Name");
                country.Continent = rset.getString("Continent");
                country.Population = rset.getInt("Population");
                country.Capital = rset.getString("Capital");
                country.Region = rset.getString("Region");
                countries.add(country);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries");
        }

        // format of the table
        System.out.printf("%-30s %-20s %-15s %-20s %-30s %15s\n",
                "Name", "Continent", "Region", "Capital", "Code", "Population");
        System.out.println("--------------------------------------------------------------------------------------------------------------");

        // Print rows
        for (Country country : countries) {
            System.out.printf("%-30s %-20s %-15s %-20s %-30s %,15d\n",
                    country.Name,
                    country.Continent,
                    country.Region,
                    country.Capital,
                    country.Code,
                    country.Population
            );
        }

    }
}
