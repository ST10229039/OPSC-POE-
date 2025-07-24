# JLT - Personal BudgetTracker App

## Overview
JLT is a comprehensive personal finance management application designed to help users track expenses, manage budgets, and achieve financial goals. The app provides features for expense recording, category management, budget tracking, financial reporting, and achievement tracking.

## Features

### Core Functionality
- **User Authentication**: Secure login and registration system
- **Expense Tracking**: Record and categorize expenses with optional photo receipts
- **Budget Management**: Set monthly budget goals and track progress
- **Financial Reports**: Generate monthly reports with expense summaries
- **Achievements**: Earn badges for financial milestones

### Key Activities
- **HomeActivity**: Dashboard with financial overview and recent transactions
- **AddExpenseActivity**: Form for adding new expenses with category selection
- **ExpenseListActivity**: View and manage all recorded expenses
- **ReportActivity**: Generate and view monthly financial reports
- **ProfileActivity**: User profile management and logout functionality

## Technical Implementation

### Architecture
- MVVM (Model-View-ViewModel) pattern
- Room Database for local data persistence
- SharedPreferences for user session management
- Navigation Drawer for app-wide navigation

### Key Components
- **ViewModels**: Manage UI-related data and business logic
- **Repositories**: Handle data operations between ViewModels and database
- **Adapters**: For RecyclerView implementations
- **PDF Generation**: For financial report export

## Installation
1. Clone the repository
2. Open project in Android Studio
3. Build and run on an emulator or physical device

## Dependencies
- AndroidX libraries
- Room Persistence Library
- Material Design Components
- ViewModel and LiveData

## Usage Instructions
1. **Registration**: Create an account via RegisterActivity
2. **Login**: Authenticate using LoginActivity
3. **Navigation**: Use the drawer menu to access all features
4. **Adding Expenses**: 
   - Navigate to AddExpenseActivity
   - Fill in expense details
   - Optionally attach a photo receipt
5. **Viewing Reports**:
   - Navigate to ReportActivity
   - Select month to view
   - Generate PDF report if needed
     
YOUTUBE LINK:

