@echo off
echo ========================================
echo ATTENDANCE MANAGEMENT SYSTEM
echo ========================================
echo.
echo Setting up everything automatically...
echo.

echo Step 1: Creating database and tables...
mysql -u root -p9298 -e "CREATE DATABASE IF NOT EXISTS attendance_db;"
mysql -u root -p9298 -e "USE attendance_db; CREATE TABLE IF NOT EXISTS students (roll INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100) NOT NULL, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);"
mysql -u root -p9298 -e "USE attendance_db; CREATE TABLE IF NOT EXISTS attendance (id INT AUTO_INCREMENT PRIMARY KEY, date DATE NOT NULL, roll INT NOT NULL, period1 VARCHAR(2) DEFAULT 'A', period2 VARCHAR(2) DEFAULT 'A', period3 VARCHAR(2) DEFAULT 'A', period4 VARCHAR(2) DEFAULT 'A', period5 VARCHAR(2) DEFAULT 'A', period6 VARCHAR(2) DEFAULT 'A', period7 VARCHAR(2) DEFAULT 'A', created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, FOREIGN KEY (roll) REFERENCES students(roll) ON DELETE CASCADE, UNIQUE KEY unique_date_roll (date, roll));"
mysql -u root -p9298 -e "USE attendance_db; INSERT IGNORE INTO students (name) VALUES ('John Doe'), ('Jane Smith'), ('Mike Johnson'), ('Sarah Wilson'), ('David Brown');"

echo Database setup completed!
echo.

echo Step 2: Checking MySQL connector...
if not exist "mysql-connector-j-9.4.0.jar" (
    echo MySQL connector not found. Please download it manually:
    echo 1. Go to: https://dev.mysql.com/downloads/connector/j/
    echo 2. Download: mysql-connector-j-9.4.0.jar
    echo 3. Place it in this folder
    echo 4. Run this script again
    echo.
    pause
    exit /b 1
)

echo MySQL connector found!
echo.

echo Step 3: Compiling application...
javac -cp mysql-connector-j-9.4.0.jar Main3/Main7.java

if %errorlevel% neq 0 (
    echo Compilation failed! Please check the error messages above.
    pause
    exit /b 1
)

echo Compilation successful!
echo.

echo Step 4: Starting Attendance Management System...
echo.
java -cp .;mysql-connector-j-9.4.0.jar Main3.Main7

pause
