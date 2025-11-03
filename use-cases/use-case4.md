# USE CASE: 4 Produce a Population Report
## CHARACTERISTIC INFORMATION

### Goal in Context
As a data analyst, I want to produce a report on total, urban, and rural populations across continents, regions, and countries so that I can understand and compare levels of urbanization and population distribution.

### Scope
Population Reporting System

### Level
Primary task.

### Preconditions
The system contains accurate and current population data for all countries, cities, and geographic regions, including classifications of urban (city) and non-urban populations.

### Success End Condition
A population report is generated showing total, urban, and rural population figures (with percentages) for each selected geographic level.

### Failed End Condition
No report is generated, or population data is incomplete or unavailable for the selected level.

### Primary Actor
Data Analyst

### Trigger
A request for demographic distribution data is initiated by the data analyst or a stakeholder.

## MAIN SUCCESS SCENARIO

1. Data analyst initiates a request to generate a population report.
2. System prompts for the geographic level of the report (continent, region, or country).
3. Data analyst selects the desired level (e.g., continent).
4. System retrieves population data:
   - Total population
   - Population living in cities (urban)
   - Population not living in cities (rural)
   - Percentage of urban and rural population
5. System generates and displays the report in a structured format.
6. Data analyst reviews and optionally exports the report for analysis or presentation.

## EXTENSIONS

3. Invalid geographic selection or missing input:
   - System prompts the user to re-enter valid geographic input.

4. Incomplete or missing population data (e.g., no city population data in a region):
   - System notifies the analyst and suggests trying a different level or region.

## SUB-VARIATIONS

   - Report showing population breakdown by continent.
   - Report showing population breakdown by region.
   - Report showing population breakdown by country.

## SCHEDULE

DUE DATE: Release: v0.1-alpha-3