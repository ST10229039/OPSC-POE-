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
## Database Schema

### Entity Relationships
The application uses Room Database with the following entities:

1. **User**
   - Stores user credentials and profile information
   - Primary key: `id` (auto-generated)
   - Fields: `username`, `email`, `password`

2. **Category**
   - Stores expense categories created by users
   - Primary key: `id` (auto-generated)
   - Fields: `name`, `user_id` (foreign key)

3. **Expense**
   - Records all user expenses
   - Primary key: `id` (auto-generated)
   - Fields: `amount`, `date`, `description`, `category_id`, `user_id`, `photo_path`

4. **BudgetGoal**
   - Stores monthly budget targets
   - Primary key: `id` (auto-generated)
   - Fields: `min_amount`, `max_amount`, `month`, `year`, `user_id`

5. **Achievement**
   - Tracks user accomplishments
   - Primary key: `id` (auto-generated)
   - Fields: `user_id`, `type`, `title`, `description`, `date_earned`, `icon_resource`
## Fragment Architecture

The application uses a fragment-based architecture for its main features, providing a modular and flexible UI structure. Each fragment handles a specific feature area while maintaining consistent navigation and user experience.

### Key Fragments

1. **AchievementsFragment**
   - Displays user achievements in a RecyclerView
   - Implements navigation drawer functionality
   - Shows achievement details in dialog boxes
   - Key Features:
     - Achievement list with icons and descriptions
     - Information dialog explaining achievements system
     - Empty state handling

2. **BudgetFragment**
   - Manages monthly budget goals
   - Visualizes budget progress with seek bar
   - Key Features:
     - Set min/max budget amounts
     - Real-time progress tracking
     - Color-coded status indicators (under/over budget)
     - Achievement unlocking for budget success

3. **CategoriesFragment**
   - Manages expense categories
   - Key Features:
     - Add/delete categories
     - Category list with swipe-to-delete
     - Empty state handling
     - Floating action button for adding categories

4. **ExpensesFragment**
   - Displays list of user expenses
   - Key Features:
     - Expense list with category information
     - "Add Expense" button navigation
     - Observes expense data changes

5. **GraphFragment**
   - Visualizes expense data
   - Key Features:
     - Bar chart for monthly spending
     - Pie chart for category breakdown
     - Date range filtering
     - Quick filter chips (today, week, month, year)
   
   Future Enhancements
-Add swipe-to-refresh functionality

-Implement more chart types and visualizations

-Add fragment transition animations

-Enhance tablet layouts with multi-pane designs

-Add search functionality to lists

     
YOUTUBE LINK: https://youtube.com/shorts/LefeHzdemBE?feature=share 

