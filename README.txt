ATTENDANCE MANAGEMENT SYSTEM - COMPLETE SETUP
=============================================

QUICK START:
1. Run setup.bat (follows all steps automatically)
2. Run run.bat to start the application

MANUAL SETUP:

STEP 1: Database Setup
- Install MySQL Server
- Run: mysql -u root -p < setup_database.sql
- This creates the database and tables

STEP 2: Download MySQL Connector
- Go to: https://dev.mysql.com/downloads/connector/j/
- Download: mysql-connector-j-9.4.0.jar
- Place it in this folder

STEP 3: Update Database Password
- Edit Main3/Main7.java
- Change line 14: DB_PASSWORD = "your_mysql_password"

STEP 4: Compile and Run
- Run: compile.bat
- Run: run.bat

FEATURES:
- Mark attendance for 7 periods per day
- Add/Remove students
- View attendance reports
- All data stored in MySQL database

FILES:
- Main3/Main7.java - Main application
- setup_database.sql - Database setup script
- compile.bat - Compilation script
- run.bat - Run application
- setup.bat - Complete setup guide

TROUBLESHOOTING:
- "MySQL JDBC Driver not found" - Download mysql-connector-j-9.4.0.jar
- "Database connection failed" - Check MySQL server and password
- "NoClassDefFoundError" - Run compile.bat first