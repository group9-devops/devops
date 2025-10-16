# USE CASE: 1 Produce a Country Report
## CHARACTERISTIC INFORMATION
### Goal in Context
As a data analyst, I want to produce a report on countries by population so that I can analyze global population trends globally and regionally.

### Scope
Population Reporting System

### Level
Primary task.

### Preconditions
The system contains accurate and current population data for all countries, including their country code , name , continent, region, population  and capital city.

### Success End Condition
A country population report is generated and available for analysis.

### Failed End Condition
No report is produced or the data is incomplete.

### Primary Actor
Data Analyst.

### Trigger
A request for country population analysis is initiated by the data analyst or a stakeholder.

## MAIN SUCCESS SCENARIO
1. Data analyst initiates a request to generate a country population report.
2. System prompts for report parameters (e.g., global, continent, region, top-N countries).
3. Data analyst selects desired filters and inputs top-N value if needed.
4. System retrieves relevant countries  data including country code , name , continent, region, population  and capital city.
5. System generates and displays the report.
6. Data analyst reviews and exports the report for further analysis or sharing.

## EXTENSIONS
3. Invalid filter or top-N input:
    * System prompts for correction and re-entry.

4. Data missing or incomplete:
    * System notifies analyst of missing data and suggests alternative filters or data sources.

## SUB-VARIATIONS

- Report filtered by continent only.
- Report filtered by region only.
- Report showing top-N populated countries globally.
- Report showing top-N populated countries regionally.


## SCHEDULE
DUE DATE: Release: v0.1-alpha-3