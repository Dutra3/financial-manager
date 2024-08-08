# Financial Manager

## Overview
The **Financial Manager** is a web application designed to help users manage their finances efficiently. It offers a sleek, user-friendly interface with both dark and light themes, with the dark theme being the default. This application is built using the latest technologies including React with TypeScript, Java 17, and Spring Boot 3.

## Features

### Theme
- **Dark and Light Themes**: The application supports both dark and light themes, with the dark theme as the default.

### Navigation Menu
- **Vertical Menu**: Positioned on the left side of the screen, the menu offers quick access to the following sections:
  - **Dashboard/Home**
  - **Transactions**
  - **Wallet (Investments)**
  - **Profile**

### Dashboard
- **User Greeting**: Displays the user's name prominently.
- **Income Overview**: A box displaying income details, including salary and other sources of revenue.
- **Total Balance**: A box showing the total balance with options to filter between different currencies (e.g., USD, BRL).
- **Monthly Expenses**: A box showing the current month's expenses, sourced from the transactions section.
- **Monthly Data Overview**: A box displaying monthly data for income and expenses, allowing users to track financial trends.
- **Financial Goals Alerts**: A box showing alerts related to financial goals, helping users stay on track.
- **Visual Reports**: Generate visual reports such as pie charts for expense categories and line charts for monthly income and expenses.

### Transactions
- **Manage Transactions**: Allows users to add, edit, or delete income and expenses.
- **Categorization**: Transactions can be categorized (e.g., food, transportation, entertainment).
- **Notes**: Users can add notes to each transaction.

### Wallet (Investments)
- **Manage Investments**: Allows users to manage their stocks, REITs, and quantities.
- **Financial Metrics**: Displays current values, P/E ratio, dividend yield, P/B ratio, and the last dividend if available.

### Profile
- **User Information**: Includes fields for name, gross/net salary, and initial balance.
- **Financial Goals and Budgets**: Set and track financial goals, and monthly budgets by category.
- **Notifications**: Alerts users when they are nearing or exceeding their budget.
- **Export Data**: Users can export their transactions and reports in formats like CSV or PDF for external analysis or sharing with accountants.

## Technologies Used

### Frontend
- **React**: Version 18.2.0
- **TypeScript**: Version 5.2.2
- **MUI (Material-UI)**: Version 5.15.20
- **MUI Icons**: Version 5.16.6
- **React Router DOM**: Version 6.23.1
- **Yarn**: Version 1.22.22

### Backend
- **Java**: Version 17
- **Spring Boot**: Version 3.3.0

## Installation

To get started with the Financial Manager project, follow these steps:

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Dutra3/financial-manager.git
   cd financial-manager

