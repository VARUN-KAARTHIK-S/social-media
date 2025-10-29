@echo off
echo Starting Attendance Management System...
echo.

REM Check if MySQL connector exists
if not exist "mysql-connector-j-9.4.0.jar" (
    echo ERROR: mysql-connector-j-9.4.0.jar not found!
    echo Please copy mysql-connector-j-9.4.0.jar to this folder.
    pause
    exit /b 1
)

REM Check if compiled class exists
if not exist "Main3\Main7.class" (
    echo ERROR: Main7.class not found!
    echo Please run compile.bat first.
    pause
    exit /b 1
)

REM Run the application
java -cp .;mysql-connector-j-9.4.0.jar Main3.Main7

pause