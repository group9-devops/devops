# USE CASE: 3 Produce a Capital City Population Report
## CHARACTERISTIC INFORMATION
### Goal in Context
As a data analyst, I want to list all cities by population so that I can compare urban populations worldwide from largest to smallest

### Scope
Population Reporting System

### Level
Primary task.

### Preconditions
The system contains accurate and current population data for all capital cities, including their country and geographic classification.

### Success End Condition
A city population report is generated, displayed in ascending order and available for analysis.

### Failed End Condition
No report is produced or the data is incomplete.

### Primary Actor
Data Analyst.

### Trigger
A request for city population analysis is initiated by the data analyst or a stakeholder.

## MAIN SUCCESS SCENARIO
1. Data analyst initiates a request to generate a city population report.
2. System prompts for report parameters (e.g., global, continent, region, top-N cities).
3. Data analyst selects desired filters and inputs top-N value if needed.
4. System retrieves relevant city data including name, country, and population.
5. System generates and displays the report in ascending order.
6. Data analyst reviews and exports the report for further analysis or sharing.

## EXTENSIONS
3. Invalid filter or top-N input:
    * System prompts for correction and re-entry.

4. Data missing or incomplete:
    * System notifies analyst of missing data and suggests alternative filters or data sources.

## SUB-VARIATIONS

- Report filtered by continent only.
- Report filtered by region only.
- Report showing top-N capital cities globally.
- Report showing top-N capital cities within a continent.

## SCHEDULE
DUE DATE: Release 1.0