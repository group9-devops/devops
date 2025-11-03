# Population Reporting System - Group 9

## Vision Statement: Population Reporting System
## Introduction
### Purpose
### Solution Overview
The application will offer a user-friendly interface to query and generate detailed population reports. It will support dynamic filtering by continent, region, country, district, and city, and allow users to specify custom parameters such as top-N populated areas. This will enhance the organisation’s ability to make data-driven decisions and produce accurate demographic insights.
### User Description
The primary users are data analysts and policy advisors with moderate technical knowledge. They require flexible reporting tools and visual summaries to support strategic planning. The application will be web-based to ensure accessibility across departments, with potential for desktop deployment if needed.
## FEATURES

### Generate Country Reports
- List all countries by population (global, continent, region).
- Display top-N populated countries based on user input.
- Include key details: code, name, continent, region, population, capital.

### Generate City Reports
- List cities by population (global, continent, region, country, district).
- Display top-N populated cities based on user input.
- Include: name, country, district, population.

### Generate Capital City Reports
- List capital cities by population (global, continent, region).
- Display top-N populated capital cities.
- Include: name, country, population.

### Generate Population Reports
- Show total population, city-dwelling population, and non-city population for each continent, region, and country.
- Include percentages for urban vs. non-urban populations.

### Language Demographics
- Report number of speakers for Chinese, English, Hindi, Spanish, and Arabic.
- Include percentage of global population for each language.

### Individual Population Queries
- Retrieve population data for specific continents, regions, countries, districts, and cities.

## Build Badges
![workflow](https://github.com/group9-devops/devops/actions/workflows/main.yml/badge.svg)

[![LICENSE](https://img.shields.io/github/license/group9-devops/devops.svg?style=flat-square)](https://github.com/group9-devops/devops/blob/master/LICENSE)

[![Releases](https://img.shields.io/github/release/group9-devops/devops/all.svg?style=flat-square)](https://github.com/group9-devops/devops/releases)

- Module : Software Engineering Methods (SETO8101)
- Year : 2
- Group: 9
- Repository: [`group9-devops`](https://github.com/group9-devops/devops)



## Build Instructions

### Prerequisites

- Java 17+
- Maven
- Docker-Compose

### Run Locally Maven

```bash
# Clone the repository
git clone https://github.com/group9-devops/devops
cd group9-devops

# Build the project using Maven
mvn clean package

# Run the app
java -jar target/devops-0.1.0.2-jar-with-dependencies.jar



```

## Docker Instructions in IntelliJ
- Build the Docker-Compose Image
- Test the image by creating a container and testing that the SQL database in connected.


## GitHub Actions (CI/CD)

Our GitHub Actions pipeline automatically

- Builds the Maven JAR
- Builds the Docker image

Workflow file: .github/workflows/main.yml

## Branching Strategy - Gitflow

We are following Gitflow with the following branches
- master: production-ready
- develop: active development
- feature: individual feature branches
- release/0.1.0: first release branch

## Code of Conduct

Our Code of Conduct outlines expected group behaviour and contribution guidelines

Location: .github/CODE_OF_CONDUCT.md

## Product Backlog

We track our tasks and progress using:

- GitHub Issues
- Zube Kanban board
- Zube Sprint Board

## Team Members and Roles

1. Chiedza Chaterera - ID: 40668762 - Product Owner  
2. Euan Birkett - ID: 40711166  - Scrum Master
3. Tulasi Rijal - ID: 40783965 - Developer One
4. Adin Carlisle - ID: 40485891 - Developer Two

