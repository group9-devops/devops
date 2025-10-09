# Population Reporting System - Group 9

## Vision Statement

### Purpose

The purpose of this application is to provide streamlined access to global population data
for analytical and reporting purposes. The system will be used by data analysts and researchers within the organisation. 
An existing SQL database containing population, country, city, and language data will serve as the foundation.

### Solution Overview

The application will offer a user-friendly interface to query and generate detailed population reports. 
It will support dynamic filtering by continent, region, country, district, city and will allow users to specify custom parameters such as top-N populated areas.

### User Description

The primary users are data analysts and researchers with moderate technical knowledge. They require flexible reporting tools and visual summaries to support strategic planning.
The application will be web-based to ensure accessibility across departments.

## Features

### Generate Country Report

1. List all countries in the world, continent and region by population in descending order.
2. Display top-N populated countries, in the world, a region and a continent where N is user input.
3. Include: code, name,continent, region, population, capital

### Generate City Report

1. List all cities in the world, continent, region, country and district by population in descending order.
2. Display top-N populated cities, where N is user input.
3. Include: name, country, district, population

### Generate Capital City Report

1. List all capital cities in the world, continent and region by population in descending order.
2. Display top-N populated capital cities, in the world, a region and a continent where N is user input.
3. Include: name, country, population

### Generate Population Report

1. Show the total population of people living in cities and those who are not in each continent, region and country.
2. Include the percentages for people living in cities and those who are not.
3. The name of each continent/country and region 
4. The total population of the continent/region/country. 

### Language Report

1. Display the number of people who speak Chinese, English, Hindi, Spanish and Arabic from greatest number to smallest
2. Include percentage of global population for each language. 

# Build Badges
![workflow](https://github.com/group9-devops/devops/actions/workflows/main.yml/badge.svg)

[![LICENSE](https://img.shields.io/github/license/group9-devops/devops.svg?style=flat-square)](https://github.com/group9-devops/devops/blob/master/LICENSE)

[![Releases](https://img.shields.io/github/release/group9-devops/devops/all.svg?style=flat-square)](https://github.com/group9-devops/devops/releases)

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
- Build the Docker Image using docker-compose
- Test the image by creating a container and testing that the database is successfully connected


## GitHub Actions (CI/CD)

Our GitHub Actions pipeline automatically

- Builds the Maven JAR
- Builds the Docker image
- Connects and Disconnects from the Database

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
- Zube.io Kanban Board and Sprint Board

## Team Members and Roles

1. Chiedza Chaterera - ID: 40668762 - Product Owner
2. Euan BirKett - ID: 40711166  - Scrum Master
3. Tulasi Rijal - ID: 40783965 - Developer one
4. Adin Carlisle - ID: 40485891 - Developer two

