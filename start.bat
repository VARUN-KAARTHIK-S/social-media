@echo off
echo ========================================
echo ATTENDANCE MANAGEMENT SYSTEM
echo ========================================
echo.

REM Check if MySQL connector exists
if not exist "mysql-connector-j-9.4.0.jar" (
    echo ERROR: MySQL connector not found!
    echo.
    echo Please download mysql-connector-j-9.4.0.jar from:
    echo https://dev.mysql.com/downloads/connector/j/
    echo.
    echo Place the JAR file in this folder and run again.
    echo.
    pause
    exit /b 1
)

REM Check if compiled class exists
if not exist "Main3\Main7.class" (
    echo Compiling application...
    call compile.bat
    if %errorlevel% neq 0 (
        echo Compilation failed! Please check the errors above.
        pause
        exit /b 1
    )
)

echo Starting application...
echo.
java -cp .;mysql-connector-j-9.4.0.jar Main3.Main7

pause
