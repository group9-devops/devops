# USE CASE: 3 Produce a Capital City Report
## CHARACTERISTIC INFORMATION
### Goal in Context
As a data analyst, I want to produce a report on capital cities by population so that I can analyze urban population trends globally and regionally.

### Scope
Population Reporting System

### Level
Primary task.

### Preconditions
The system contains accurate and current population data for all capital cities, including their country and geographic classification.

### Success End Condition
A capital city population report is generated and available for analysis.

### Failed End Condition
No report is produced or the data is incomplete.

### Primary Actor
Data Analyst.

### Trigger
A request for urban population analysis is initiated by the data analyst or a stakeholder.

## MAIN SUCCESS SCENARIO
1. Data analyst initiates a request to generate a capital city population report.
2. System prompts for report parameters (e.g., global, continent, region, top-N cities).
3. Data analyst selects desired filters and inputs top-N value if needed.
4. System retrieves relevant capital city data including name, country, and population.
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
- Report showing top-N capital cities globally.
- Report showing top-N capital cities within a continent.

## SCHEDULE
DUE DATE: Release: v0.1-alpha-3