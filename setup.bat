@echo off
echo Setting up Attendance Management System...
echo.

echo Step 1: Setting up MySQL Database...
echo Please run this command in MySQL:
echo mysql -u root -p ^< setup_database.sql
echo.
echo Press any key after you have set up the database...
pause

echo.
echo Step 2: Download MySQL Connector...
echo Please download mysql-connector-j-9.4.0.jar from:
echo https://dev.mysql.com/downloads/connector/j/
echo.
echo Place the JAR file in this folder.
echo.
echo Press any key after you have downloaded and placed the JAR file...
pause

echo.
echo Step 3: Update Database Password...
echo Please edit Main3/Main7.java and update line 14:
echo private static final String DB_PASSWORD = "your_mysql_password";
echo.
echo Press any key after you have updated the password...
pause

echo.
echo Step 4: Compiling...
call compile.bat

echo.
echo Setup complete! You can now run the application using run.bat
pause