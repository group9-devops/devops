package com.napier.sem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * The LanguageReport class retrieves and displays
 * information about the number of people who speak selected languages
 * and their share of the world population.
 * <p>
 * It uses the 'country' and 'countrylanguage' tables to calculate:
 * - Number of speakers per language
 * - Percentage of world population speaking each language
 * </p>
 */
public class LanguageReport {

    private Connection con;


    /**
     * Constructor to inject database connection.
     *
     * @param con the active database connection
     */
    public LanguageReport(Connection con) {
        this.con = con;
    }

    /**
     * Retrieves a report of the selected languages with their
     * estimated number of speakers and percentage of the world population.
     *
     * @return ArrayList of CountryLanguage objects
     */
    public ArrayList<CountryLanguage> retrieveLanguageSpeakers() {
        if (con == null) {
            return new ArrayList<>();
        }

        String sql = """
                SELECT 
                    c.Language,
                    SUM(country.Population * (c.Percentage / 100)) AS NumberOfSpeakers,
                    (SUM(country.Population * (c.Percentage / 100)) / 
                        (SELECT SUM(Population) FROM country) * 100
                    ) AS WorldPercentage
                FROM countrylanguage c
                JOIN country ON country.Code = c.CountryCode
                WHERE c.Language IN ("Chinese", "English", "Hindi", "Spanish", "Arabic")
                GROUP BY c.Language
                ORDER BY NumberOfSpeakers DESC;
                """;

        return executeLanguageQuery(sql);
    }

    /**
     * Executes the SQL query and maps results to CountryLanguage objects.
     *
     * @param sql SQL query string
     * @return ArrayList of CountryLanguage objects
     */
    private ArrayList<CountryLanguage> executeLanguageQuery(String sql) {
        ArrayList<CountryLanguage> languages = new ArrayList<>();

        if (con == null) {
            return languages;
        }

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                CountryLanguage lang = new CountryLanguage();
                lang.Language = rset.getString("Language");
                lang.NumberOfSpeakers = rset.getLong("NumberOfSpeakers");
                lang.WorldPercentage = rset.getDouble("WorldPercentage");
                languages.add(lang);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to execute language query.");
            return null;
        }

        return languages;
    }

    /**
     * Prints the language report to the console in a formatted table.
     *
     * @param languages List of CountryLanguage objects
     */
    public void printLanguageReport(ArrayList<CountryLanguage> languages) {
        if (languages == null || languages.isEmpty()) {
            System.out.println("No language data available.");
            return;
        }

        System.out.printf("%-15s %20s %20s%n", "Language", "Number of Speakers", "World %");
        System.out.println("---------------------------------------------------------------");

        for (CountryLanguage lang : languages) {
            if (lang == null) continue;

            System.out.printf("%-15s %,20d %19.2f%%%n",
                    lang.Language, lang.NumberOfSpeakers, lang.WorldPercentage);
        }
    }

    /**
     * Outputs a list of capital cities to a Markdown file.
     *
     * @param languages List of capital cities to output
     * @param filename The name of the Markdown file to create
     */
    public void outputLanguages(ArrayList<CountryLanguage> languages, String filename) {
        if (languages == null || languages.isEmpty()) {
            System.out.println("No capital cities to output.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        // Markdown table header
        sb.append("| Language | Number of Speakers | World Percentage |\r\n");
        sb.append("| --- | --- | --- |\r\n");

        // Loop through all capital cities
        for (CountryLanguage language : languages) {
            if (language == null) continue;
            sb.append("| ")
                    .append(language.Language).append(" | ")
                    .append(language.NumberOfSpeakers).append(" | ")
                    .append(language.WorldPercentage).append(" |\r\n");
        }

        try {
            // Create reports folder if it does not exist
            new java.io.File("./reports/").mkdirs();

            // Write Markdown to file
            java.io.BufferedWriter writer = new java.io.BufferedWriter(
                    new java.io.FileWriter("./reports/" + filename));
            writer.write(sb.toString());
            writer.close();
            System.out.println("Capital cities report written to ./reports/" + filename);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.out.println("Failed to write capital cities report.");
        }
    }
}
