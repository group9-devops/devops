package com.napier.sem;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

/**
 * Represents details of a  City
 */
public class City {
    /**
     * City's name
     */
    public String name;
    /**
     * City's country (Country Code)
     */
    public String country;
    /**
     * City's District
     */
    public String district;
    /**
     * City's Population
     */
    public int population;
    /**
     * True or False if City is a capital
     */
    public boolean isCapital;
    /**
     * City's ID in DB
     */
    public int ID;
}
