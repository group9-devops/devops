# USE CASE: 5 – Produce a Language Report
## CHARACTERISTIC INFORMATION

### Goal in Context
As a data analyst, I want to generate a report showing the number of people who speak specific languages so I can assess the global distribution and prominence of major world languages.

### Scope
Population Reporting System

### Level
Primary task

### Preconditions

- The system has accurate and up-to-date population data for all countries.

- The database includes information on the percentage of the population that speaks specific languages (e.g., Chinese, English, Hindi, Spanish, Arabic).

- The world population is known for percentage calculations.

### Success End Condition

A language report is generated showing the number of speakers per language and the percentage of the world population speaking each.

### Failed End Condition

No report is generated, or the data is incomplete or inaccurate (e.g., missing population or language percentage).

### Primary Actor
Data Analyst

### Trigger
A request is made to analyze the global distribution of major languages.

## MAIN SUCCESS SCENARIO

1. Data analyst initiates a request to generate a language report.

2. System identifies the specific languages to include in the report (e.g., Chinese, English, Hindi, Spanish, Arabic).

3. System queries the database to retrieve:

4. Each country where the language is spoken

5. The percentage of speakers in that country

6. The total population of the country

7. System calculates:

   - The number of speakers in each country (country population × language %)

   - The global total number of speakers per language

   - The percentage of the world population that speaks each language

8. System generates and displays the report sorted from most spoken to least spoken language.

9. Data analyst reviews, exports, or shares the report for further use.

## EXTENSIONS

3. Language data missing for a country
   - System excludes that country from calculations and notifies the analyst.

4. World population data missing or invalid
   - System notifies analyst and halts percentage calculation.

5. No valid language data found
   - System displays message: "No data available for selected languages."

## SUB-VARIATIONS

- Report includes only selected languages

- Report includes all languages available in the database

- Analyst selects top N languages by number of speakers (optional enhancement)

## SCHEDULE

Due Date: Release 1.0