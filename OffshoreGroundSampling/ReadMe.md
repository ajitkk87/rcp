# Offshore Ground Sampling Application

This project is an Eclipse Rich Client Platform (RCP) application designed to manage and analyze offshore ground sampling data.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)

## Features

- **Sample Management**: Add, edit, and delete ground sampling data, including:
  - Sample ID
  - Location
  - Date Collected
  - Unit Weight (kN/m³)
  - Water Content (%)
  - Shear Strength (kPa)
- **Data Validation**: Ensure input data adheres to specified ranges for accuracy.
- **Backend Integration**: Communicate with a Spring Boot backend for data persistence and retrieval.
- **Statistics**: View average water content and identify samples exceeding threshold values.
- **Optional Graph Visualization**: Display the relationship between unit weight and water content using integrated graphing tools.

## Prerequisites

- **Git**: [Download and install Git](https://git-scm.com/downloads)
- **Java 21 or higher**: [Download and install Java](https://www.oracle.com/java/technologies/downloads/)
- **Eclipse IDE for RCP and RAP Developers**: [Download Eclipse IDE](https://www.eclipse.org/downloads/packages/)

## Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/ajitkk87/rcp.git
   
   
## Usage

 **Launch Application**:

 - Import Project as an existing project in eclipse.
 - Open OffshoreGroundSampling.product and "Launch as Eclipse Application" or "Run as Eclipse Application"
![Application Diagram](icons/Running_Application.jpg)

