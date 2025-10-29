@echo off
echo Compiling Attendance Management System...
echo.

REM Check if MySQL connector exists
if not exist "mysql-connector-j-9.4.0.jar" (
    echo ERROR: mysql-connector-j-9.4.0.jar not found!
    echo Please copy mysql-connector-j-9.4.0.jar to this folder.
    pause
    exit /b 1
)

REM Compile the Java files
javac -cp mysql-connector-j-9.4.0.jar Main3/Main7.java

if %errorlevel% equ 0 (
    echo.
    echo Compilation successful!
    echo.
    echo To run the application, use: run.bat
) else (
    echo.
    echo Compilation failed!
    echo Please check the error messages above.
)

pause