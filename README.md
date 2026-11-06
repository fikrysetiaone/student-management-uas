# Student Management System - UAS OOP

Project Java Swing + MySQL + JDBC + JasperReports.

## Fitur
- Create, Read, Update, Delete student.
- UI dengan gradient background.
- Avatar setiap mahasiswa memiliki warna dan inisial yang ditentukan dari Student ID dan nama.
- JasperReports PDF dengan logo, rentang Student ID (`BETWEEN`), nomor halaman dan jumlah halaman.
- Screenshot otomatis ke folder `captures/` setelah Create, Update, Delete, dan pembuatan report.

## Kebutuhan
- JDK 21
- IntelliJ IDEA (atau IDE Java yang mendukung Maven)
- MySQL Server / XAMPP / Laragon
- Maven (IntelliJ dapat mengimpor Maven project)

## Langkah menjalankan
1. Jalankan MySQL.
2. Buka phpMyAdmin atau MySQL Workbench.
3. Jalankan seluruh isi file `database.sql`.
4. Buka project ini di IntelliJ IDEA melalui `File > Open`, pilih folder project yang berisi `pom.xml`.
5. Tunggu Maven mengunduh dependencies.
6. Buka `src/main/resources/db.properties`, sesuaikan username dan password MySQL.
7. Jalankan `src/main/java/com/fikri/studentmanagement/Main.java`.
8. Tes Create, pilih baris lalu Update, pilih baris lalu Delete.
9. Klik Generate PDF Report, isi Student ID From dan Student ID To.
10. Periksa file PDF di folder `reports/` dan screenshot di folder `captures/`.

## Database
Database: `student_master`
Table: `students`
Columns:
- `student_id` INT PRIMARY KEY
- `name` VARCHAR(100)
- `email` VARCHAR(150)

## Catatan screenshot
Screenshot dibuat oleh aplikasi ketika aksi berhasil. File yang dihasilkan:
- `captures/create.png`
- `captures/update.png`
- `captures/delete.png`
- `captures/report.png`

Jalankan aksi masing-masing minimal satu kali agar semua file PNG dibuat.

## Push ke GitHub
Buat repository baru bernama `student-management-uas`, lalu dari terminal di root project:

```bash
git init
git add .
git commit -m "Complete Student Management UAS project"
git branch -M main
git remote add origin https://github.com/USERNAME/student-management-uas.git
git push -u origin main
```

Ganti `USERNAME` dengan username GitHub sendiri.
