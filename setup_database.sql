-- Attendance Management System Database Setup
-- Run this script to create the database and tables

-- Create database
CREATE DATABASE IF NOT EXISTS attendance_db;
USE attendance_db;

-- Create students table
CREATE TABLE IF NOT EXISTS students (
    roll INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create attendance table
CREATE TABLE IF NOT EXISTS attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    roll INT NOT NULL,
    period1 VARCHAR(2) DEFAULT 'A',
    period2 VARCHAR(2) DEFAULT 'A',
    period3 VARCHAR(2) DEFAULT 'A',
    period4 VARCHAR(2) DEFAULT 'A',
    period5 VARCHAR(2) DEFAULT 'A',
    period6 VARCHAR(2) DEFAULT 'A',
    period7 VARCHAR(2) DEFAULT 'A',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (roll) REFERENCES students(roll) ON DELETE CASCADE,
    UNIQUE KEY unique_date_roll (date, roll)
);

-- Insert sample students
INSERT INTO students (name) VALUES 
('John Doe'),
('Jane Smith'),
('Mike Johnson'),
('Sarah Wilson'),
('David Brown');

SELECT 'Database setup completed successfully!' AS message;