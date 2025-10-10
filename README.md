# Population Reporting System - Group 9

# Build Badges
![workflow](https://github.com/group9-devops/devops/actions/workflows/main.yml/badge.svg)

[![LICENSE](https://img.shields.io/github/license/group9-devops/devops.svg?style=flat-square)](https://github.com/group9-devops/devops/blob/master/LICENSE)

[![Releases](https://img.shields.io/github/release/group9-devops/devops/all.svg?style=flat-square)](https://github.com/group9-devops/devops/releases)

- Module : Software Engineering Methods (SETO8101)
- Year : 2
- Group: 9
- Repository: [`group9-devops`](https://github.com/group9-devops/devops)

## Code Review 1 - Checklist Status

1. GitHub Project created - **Completed** - [`Link to repo`](https://github.com/group9-devops/devops)

2. Product Backlog created - **Completed** - Located at GitHub Project Board

3. Maven JAR build working - **Completed** - Maven Build, JAR building

4. Dockerfile created - **Completed** - Created Docker file

5. Add GitHub Actions build workflow - **Completed**

6. Create GitFlow branches (master, develop, release/0.1.0) -**Completed**

7. First release created - **Completed** -- v0.1-alpha-2 tagged

8. Create a Code of Conduct - **Completed**

## Build Instructions

### Prerequisites

- Java 17+
- Maven
- Docker

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
- Build the Docker Image
- Test the image by creating a container and testing the Mongo DB-Server


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
- GitHub Project board

## Team Members and Roles

1. Chiedza Chaterera - ID: 40668762 - Scrum Master  
2. Euan Birkett - ID: 40711166  - Product Owner
3. Tulasi Rijal - ID: 40783965 - Developer One
4. Adin Carlisle - ID: 40485891 - Developer Two

