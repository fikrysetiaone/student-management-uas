CREATE DATABASE IF NOT EXISTS student_master;
USE student_master;

CREATE TABLE IF NOT EXISTS students (
    student_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE
);

-- Data contoh (boleh dijalankan sekali saja)
INSERT IGNORE INTO students (student_id, name, email) VALUES
(1001, 'Fikri Setiawan', 'fikri@example.com'),
(1002, 'Budi Santoso', 'budi@example.com'),
(1003, 'Siti Aminah', 'siti@example.com'),
(1004, 'Andi Pratama', 'andi@example.com');
