# USE CASE: 2 – Produce a City Report

## CHARACTERISTIC INFORMATION

### Goal in Context
As a data analyst, I want to produce a report listing all cities by population so that I can analyze and compare urban populations by country, region, continent, or globally.

### Scope
Population Reporting System

### Level
Primary task

### Preconditions

- The system contains accurate and up-to-date population data for all cities.

- Each city is linked to a country, district, region, and continent in the database.

### Success End Condition

A city population report is successfully generated and made available for analysis, ordered from largest to smallest population.

### Failed End Condition

No report is produced, or the returned data is incomplete, incorrect, or empty.

### Primary Actor
Data Analyst

### Trigger

The data analyst or a stakeholder initiates a request to analyze city population data.

## MAIN SUCCESS SCENARIO

1. Data analyst initiates a request to generate a city population report.
2. System prompts the user for report parameters (e.g., global, continent, region, country, district, or top-N cities).
3. Data analyst selects filters or inputs a top-N value if needed.
4. System queries the database to retrieve relevant city data including:
   - City name
   - Country name
   - District
   - Population

5. System generates and displays the report, sorted from largest to smallest population (descending order).
6. Data analyst reviews and optionally exports or saves the report for further use.

## EXTENSIONS
3. Invalid filter or top-N input:
    * System prompts for correction and re-entry.

4. Data missing or incomplete:
    * System notifies analyst of missing data and suggests alternative filters or data sources.


## SUB-VARIATIONS

- Report filtered by continent only
- Report filtered by region only
- Report filtered by country only
- Report filtered by district only
- Report showing top-N cities globally
- Report showing top-N cities by continent, region, country, or district

## SCHEDULE

DUE DATE: Release: v0.1-alpha-3