package com.napier.sem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * The Urban Report class is responsible for retrieving and displaying
 * information about the population and urban population from the database.
 */
public class UrbanReport {
    public double population;
    public double urbanPopulation;
    public double percentage;
    DecimalFormat numberFormat = new DecimalFormat("#,###");
    DecimalFormat percentageFormat = new DecimalFormat("#.##");

    /**
     * Retrieves the total population of the world.
     *
     * @param con the active database connection
     */
    public void getPopulationOfWorld(Connection con) {
        String sql = "SELECT SUM(population) FROM country";
        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rset = stmt.executeQuery()) {
            if (rset.next()) {
                population = rset.getDouble(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get world population");
            population = 0;
        }
    }

    /**
     * Retrieves the total urban population of the world.
     *
     * @param con the active database connection
     */
    public void getUrbanPopulation(Connection con) {
        String sql = "SELECT SUM(population) FROM city";
        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rset = stmt.executeQuery()) {
            if (rset.next()) {
                urbanPopulation = rset.getDouble(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get urban population");
            urbanPopulation = 0;
        }
    }

    /**
     * Retrieves the total population of a specific region.
     *
     * @param con    the active database connection
     * @param region the region to query
     */
    public void getPopulationOfRegion(Connection con, String region) {
        String sql = "SELECT SUM(population) FROM country WHERE Region = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, region);
            try (ResultSet rset = stmt.executeQuery()) {
                if (rset.next()) {
                    population = rset.getDouble(1);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population of region");
            population = 0;
        }
    }

    /**
     * Retrieves the total urban population of a specific region.
     *
     * @param con    the active database connection
     * @param region the region to query
     */
    public void getUrbanPopulationOfRegion(Connection con, String region) {
        String sql = "SELECT SUM(city.population) FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Region = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, region);
            try (ResultSet rset = stmt.executeQuery()) {
                if (rset.next()) {
                    urbanPopulation = rset.getDouble(1);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get urban population of region");
            urbanPopulation = 0;
        }
    }

    /**
     * Retrieves the total population of a specific continent.
     *
     * @param con       the active database connection
     * @param continent the continent to query
     */
    public void getPopulationOfContinent(Connection con, String continent) {
        String sql = "SELECT SUM(population) FROM country WHERE Continent = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, continent);
            try (ResultSet rset = stmt.executeQuery()) {
                if (rset.next()) {
                    population = rset.getDouble(1);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population of continent");
            population = 0;
        }
    }

    /**
     * Retrieves the total urban population of a specific continent.
     *
     * @param con       the active database connection
     * @param continent the continent to query
     */
    public void getUrbanPopulationOfContinent(Connection con, String continent) {
        String sql = "SELECT SUM(city.population) FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Continent = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, continent);
            try (ResultSet rset = stmt.executeQuery()) {
                if (rset.next()) {
                    urbanPopulation = rset.getDouble(1);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get urban population of continent");
            urbanPopulation = 0;
        }
    }

    /**
     * Retrieves the population of a specific country.
     *
     * @param con     the active database connection
     * @param country the country to query
     */
    public void getPopulationOfCountry(Connection con, String country) {
        String sql = "SELECT population FROM country WHERE Name = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, country);
            try (ResultSet rset = stmt.executeQuery()) {
                if (rset.next()) {
                    population = rset.getDouble(1);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population of country");
            population = 0;
        }
    }

    /**
     * Retrieves the urban population of a specific country.
     *
     * @param con     the active database connection
     * @param country the country to query
     */
    public void getUrbanPopulationOfCountry(Connection con, String country) {
        String sql = "SELECT SUM(city.population) FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Name = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, country);
            try (ResultSet rset = stmt.executeQuery()) {
                if (rset.next()) {
                    urbanPopulation = rset.getDouble(1);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get urban population of country");
            urbanPopulation = 0;
        }
    }

    /**
     * Retrieves the population of a specific city.
     *
     * @param con  the active database connection
     * @param city the city to query
     */
    public void getPopulationOfCity(Connection con, String city) {
        String sql = "SELECT population FROM city WHERE Name = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, city);
            try (ResultSet rset = stmt.executeQuery()) {
                if (rset.next()) {
                    population = rset.getDouble(1);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population of city");
            population = 0;
        }
    }

    /**
     * Generates lists of regions, countries, and cities, then generates continent report.
     *
     * @param con active database connection
     */
    public void generateReportLists(Connection con) {
        String[] continents = {
                "Africa","Antarctica","Asia","Europe",
                "North America","Oceania","South America"
        };

        List<String> regions = new ArrayList<>();
        List<String> countries = new ArrayList<>();
        List<String> cities = new ArrayList<>();
        try {
            // 1. Regions
            try (Statement stmt = con.createStatement()) {
                ResultSet rset = stmt.executeQuery(
                        "SELECT DISTINCT Region FROM country WHERE Region IS NOT NULL"
                );
                while (rset.next()) {
                    regions.add(rset.getString("Region"));
                }
            }

            // 2. Countries
            try (Statement stmt = con.createStatement()) {
                ResultSet rset = stmt.executeQuery(
                        "SELECT DISTINCT Name FROM country ORDER BY Name"
                );
                while (rset.next()) {
                    countries.add(rset.getString("Name"));
                }
            }

            // 3. Cities
            try (Statement stmt = con.createStatement()) {
                ResultSet rset = stmt.executeQuery(
                        "SELECT DISTINCT Name FROM city ORDER BY Name"
                );
                while (rset.next()) {
                    cities.add(rset.getString("Name"));
                }
            }

        } catch (Exception e) {
            System.out.println("List creation error");
            System.out.println(e.getMessage());
        }

        generateContinentReport(con, continents,"ContinentUrbanReport.md");
        generateRegionReport(con, regions,"RegionalUrbanReport.md");
        generateCountryReport(con, countries,"CountryUrbanReport.md");
    }

    public void generateContinentReport(Connection con, String[] continents, String filename){
        StringBuilder sb = new StringBuilder();
        // Markdown table header
        sb.append("| Continent | Population | Urban Population | Urbanisation Percentage |\r\n");
        sb.append("| --- | --- | --- | --- |\r\n");

        // Loop through all continents and generate values
        for (String continent : continents){
            getPopulationOfContinent(con,continent);
            getUrbanPopulationOfContinent(con,continent);
            if (population == 0) {
                percentage = 0;
            } else {
                percentage = (urbanPopulation * 100.0) / population;
            }

            sb.append("| ")
                    .append(continent).append(" | ")
                    .append(numberFormat.format(population)).append(" | ")
                    .append(numberFormat.format(urbanPopulation)).append(" | ")
                    .append(percentageFormat.format(percentage))
                    .append(" |\r\n");

        }

        try {
            // Create reports folder if it does not exist
            new java.io.File("./reports/").mkdirs();

            // Write Markdown to file
            java.io.BufferedWriter writer = new java.io.BufferedWriter(
                    new java.io.FileWriter("./reports/" + filename));
            writer.write(sb.toString());
            writer.close();
            System.out.println("Continental urbanisation report written to ./reports/" + filename);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.out.println("Failed to write continental urbanisation report.");
        }
    }

    public void generateRegionReport(Connection con, List<String> regions, String filename){
        StringBuilder sb = new StringBuilder();
        // Markdown table header
        sb.append("| Region | Population | Urban Population | Urbanisation Percentage |\r\n");
        sb.append("| --- | --- | --- | --- |\r\n");

        // Loop through all continents and generate values
        for (String region : regions){
            getPopulationOfRegion(con,region);
            getUrbanPopulationOfRegion(con,region);
            if (population == 0) {
                percentage = 0;
            } else {
                percentage = (urbanPopulation * 100.0) / population;
            }

            sb.append("| ")
                    .append(region).append(" | ")
                    .append(numberFormat.format(population)).append(" | ")
                    .append(numberFormat.format(urbanPopulation)).append(" | ")
                    .append(percentageFormat.format(percentage))
                    .append(" |\r\n");

        }

        try {
            // Create reports folder if it does not exist
            new java.io.File("./reports/").mkdirs();

            // Write Markdown to file
            java.io.BufferedWriter writer = new java.io.BufferedWriter(
                    new java.io.FileWriter("./reports/" + filename));
            writer.write(sb.toString());
            writer.close();
            System.out.println("Regional urbanisation report written to ./reports/" + filename);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.out.println("Failed to write regional urbanisation report.");
        }
    }

    public void generateCountryReport(Connection con, List<String> countries, String filename){
        StringBuilder sb = new StringBuilder();
        // Markdown table header
        sb.append("| Country | Population | Urban Population | Urbanisation Percentage |\r\n");
        sb.append("| --- | --- | --- | --- |\r\n");

        // Loop through all continents and generate values
        for (String country : countries){
            getPopulationOfCountry(con,country);
            getUrbanPopulationOfCountry(con,country);
            if (population == 0) {
                percentage = 0;
            } else {
                percentage = (urbanPopulation * 100.0) / population;
            }

            sb.append("| ")
                    .append(country).append(" | ")
                    .append(numberFormat.format(population)).append(" | ")
                    .append(numberFormat.format(urbanPopulation)).append(" | ")
                    .append(percentageFormat.format(percentage))
                    .append(" |\r\n");

        }

        try {
            // Create reports folder if it does not exist
            new java.io.File("./reports/").mkdirs();

            // Write Markdown to file
            java.io.BufferedWriter writer = new java.io.BufferedWriter(
                    new java.io.FileWriter("./reports/" + filename));
            writer.write(sb.toString());
            writer.close();
            System.out.println("Country urbanisation report written to ./reports/" + filename);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.out.println("Failed to write country urbanisation report.");
        }
    }
}
