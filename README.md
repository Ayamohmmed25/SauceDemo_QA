# SauceDemo QA Automation Project

![Java](https://img.shields.io/badge/Java-JDK_27-orange?logo=openjdk&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-4.49.0-43B02A?logo=selenium&logoColor=white)
![TestNG](https://img.shields.io/badge/TestNG-7.9.0-FF6F00)
![Maven](https://img.shields.io/badge/Maven-Build_Tool-C71A36?logo=apachemaven&logoColor=white)

## Overview

A Software Quality Assurance project focused on Manual Testing and Test Automation of the [SauceDemo](https://www.saucedemo.com/) web application.

The project demonstrates practical QA skills through test case design, test execution, bug reporting, screenshot documentation, and automated testing using Java, Selenium WebDriver, and TestNG.

## Tools & Technologies

| Category | Technology |
|---|---|
| Programming Language | Java (JDK 27) |
| Automation Tool | Selenium WebDriver 4.49.0 |
| Test Framework | TestNG 7.9.0 |
| Build Tool | Maven |
| IDE | IntelliJ IDEA |
| Browser | Google Chrome |
| Version Control | Git & GitHub |

## Testing Scope

- Functional Testing
- Positive Testing
- Negative Testing
- Regression Testing
- Checkout Process Testing
- Form Validation Testing

## Test Users

| User | Testing Purpose |

| `standard_user` | Functional and checkout workflow testing |
| `error_user` | Testing error-related application behavior |

## Automated Test Scenarios

The Selenium automation suite covers the following scenarios:

- Login with valid credentials.
- Add Sauce Labs Backpack to the shopping cart.
- Navigate to the shopping cart.
- Proceed to checkout.
- Validate checkout information fields.
- Test checkout behavior when required information is missing.
- Complete the checkout process.

## Bug Reporting & Test Documentation

The project includes:

- Documented Manual Test Cases.
- Bug Reports with steps to reproduce.
- Expected vs. Actual Results.
- Test Execution Status (Pass / Fail).
- Screenshot Evidence for test execution and identified defects.

### Identified Bug

**Checkout – Missing Last Name Validation**

The application allows the user to proceed to the Checkout Overview page without entering a Last Name, instead of displaying the expected validation error.

- Affected Module: Checkout – Your Information
- Bug Type: Functional / Validation
- Priority: Medium
- Severity: Medium

## Project Structure

```text
SauceDemo_QA/
│
├── src/
│   └── test/
│       └── java/
│           └── checkout.java
│
├── Test-Documentation/
│   ├── Test-Evidence/
│   │   └── Screenshot Evidence
│   ├── Test-Cases.xlsx
│   └── Bug-Reports.xlsx
│
├── pom.xml
└── README.md
```

## How to Run the Project

### Prerequisites

- Java JDK 27
- Maven
- Google Chrome
- IntelliJ IDEA

### Execution

Clone the repository:

```bash
git clone https://github.com/Ayamohmmed25/SauceDemo_QA.git
```

Navigate to the project directory:

```bash
cd SauceDemo_QA
```

Run the automated tests:

```bash
mvn clean test
```

TestNG execution reports are available in:

```text
target/surefire-reports/
```

## Test Environment

- Operating System: Windows 11
- Browser: Google Chrome
- Programming Language: Java
- Selenium WebDriver: 4.49.0
- TestNG: 7.9.0
- Build Tool: Maven

## Application Under Test

[SauceDemo](https://www.saucedemo.com/)

## Author 

Aya Mohammed Nazzal
Software Engineering Graduate | Aspiring Software Quality Assurance Engineer

- LinkedIn: www.linkedin.com/in/ayanazzall
- Email: ayamohnazzal@gmail.com
