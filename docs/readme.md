# 🧩 Spring Reborn Framework – Project Documentation

Dokumentasi ini menjelaskan cara menjalankan proyek, mengatur konfigurasi environment, serta menggunakan fitur generator entity berbasis skema JSON.

---

## 📦 Requirements

- Java 21
- Jenv 0.5.7
- Maven 3.9.9
- IDE seperti IntelliJ IDEA, VS Code, atau lainnya
- PostgreSQL / MySQL
- Terminal / CLI

---

## 🚀 Menjalankan Aplikasi

Gunakan perintah berikut untuk menjalankan aplikasi Spring Boot:

```bash
mvn spring-boot:run
```

---

## ⚙️ Konfigurasi Environment (`.env`)

Aplikasi menggunakan file `.env` untuk konfigurasi runtime, seperti koneksi database.

### 1. Salin file contoh `.env.example`

```bash
cp .env.example .env
```

### 2. Ubah isi file `.env` sesuai konfigurasi Anda

```env
#customize with your project package
ENTITY_PACKAGE=id.co.xinix.spring.module

# if using postgres please use this
#DATABASE_DRIVER=org.postgresql.Driver
#HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect

DATABASE_DRIVER=org.mysql.Driver
HIBERNATE_DIALECT=org.hibernate.dialect.MySQLDialect
DATABASE_TYPE=REPLACE_WITH_YOUR_DATABASE_TYPE

DATASOURCE_URL=jdbc:REPLACE_WITH_YOUR_DATABASE_TYPE://localhost:REPLACE_WITH_YOUR_PORT_DATABASE/REPLACE_WITH_YOUR_NAME_DATABASE
DATASOURCE_USERNAME=REPLACE_WITH_YOUR_USERNAME_DATABASE
DATASOURCE_PASSWORD=REPLACE_WITH_YOUR_PASSWORD_DATABASE

SERVER_PORT=8080

MAIL_HOST=REPLACE_WITH_YOUR_HOST_SMTP
MAIL_PORT=REPLACE_WITH_YOUR_PORT_SMTP
MAIL_USERNAME=REPLACE_WITH_YOUR_USERNAME_SMTP
MAIL_PASSWORD=REPLACE_WITH_YOUR_PASSWORD_SMTP
MAIL_PROTOCOL=smtp
MAIL_TLS=true
MAIL_PROPERTIES_AUTH=true
MAIL_PROPERTIES_STARTTLS_ENABLE=true
```

> Pastikan file `.env` berada di direktori root proyek.

---

## 🛠️ Generator Entity

Fitur generator ini memungkinkan Anda membuat module/entity secara otomatis berdasarkan skema JSON.

### 📁 Struktur Folder

```bash
project-root/
├── schema/
│   ├── UserInformation.json
│   └── Book.json
├── .env.example
├── .env
└── ...
```

### 📌 Langkah-langkah Penggunaan

1. Buat file schema JSON di folder `schema/`.  
   Contoh: `UserInformation.json`

2. Jalankan perintah berikut:

```bash
mvn exec:java -Dexec.args="UserInformation.json auth"
```

- Argumen pertama: nama file schema (`UserInformation.json`)
- Argumen kedua *(opsional)*: nama modul atau bounded context (`auth`)

Jika argumen kedua tidak diberikan, maka default modul (misalnya `spring`) akan digunakan.

### Contoh Tanpa Modul

```bash
mvn exec:java -Dexec.args="UserInformation.json"
```

### Contoh Dengan Modul

```bash
mvn exec:java -Dexec.args="Book.json library"
```

---

## ✅ Best Practices

- Gunakan penamaan schema JSON dengan format PascalCase:

  ```text
  UserInformation.json
  BookDetail.json
  ```

- Simpan semua schema di dalam folder `schema/`
- Gunakan modul/bounded context yang terpisah sesuai domain
- Jangan ubah kode hasil generate tanpa dokumentasi tambahan

---

# 🔐 Authorization Guide (Spring Security)

Aplikasi ini menggunakan **Spring Security** dengan pendekatan otorisasi berbasis **role** dan **ownership** menggunakan anotasi `@PreAuthorize`.

## ✅ Akses Berdasarkan Role (Role-Based Access Control)

JWT token yang digunakan menyimpan informasi `role`, dan dapat digunakan dalam anotasi `@PreAuthorize`:

### Contoh Role Tunggal

```java
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<?> adminOnly() {
    // hanya pengguna dengan role ADMIN yang bisa mengakses
}
```

### Contoh Beberapa Role

```java
@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
public ResponseEntity<?> adminOrSuperadmin() {
    // bisa diakses oleh ADMIN atau SUPERADMIN
}
```

> Catatan: `hasRole('X')` secara otomatis mencari authority `ROLE_X`.  
> Jika kamu menggunakan authority tanpa prefix `ROLE_`, gunakan `hasAuthority('x')`.

### Contoh Authority Langsung

```java
@PreAuthorize("hasAuthority('superadmin')")
```

---

## 🧑 Ownership-Based Access Control

Fitur ini membatasi akses agar hanya pemilik data yang dapat melakukan operasi tertentu.

### Contoh Dasar

```java
@PreAuthorize("@ownershipGuard.isOwner('example', #id)")
public ResponseEntity<?> updateExample(@PathVariable Long id) {
    // hanya pemilik resource example dengan id yang dimaksud yang dapat mengakses
}
```

### Parameter

- `'example'`: nama entitas atau nama bean repository (`ExampleRepository`)
- `#id`: ID dari resource yang akan dicek

### Penamaan Entitas Lain

Kamu bisa menggunakan entitas lain seperti:

```java
@PreAuthorize("@ownershipGuard.isOwner('userProfile', #id)")
@PreAuthorize("@ownershipGuard.isOwner('userPremiumInformation', #id)")
```

Pastikan kamu memiliki repository dengan nama `UserProfileRepository`, `UserPremiumInformationRepository`, dll.

---

## 🔁 Kombinasi Role + Ownership

```java
@PreAuthorize("hasRole('ADMIN') or @ownershipGuard.isOwner('userProfile', #id)")
public ResponseEntity<?> updateOrAdminAccess(@PathVariable Long id) {
    // ADMIN bisa akses semua, selain itu hanya pemilik yang bisa
}
```

---

## ⚠️ Exception Handling

Aplikasi ini telah menangani berbagai jenis error termasuk:

- `AccessDeniedException`: akan menghasilkan response `403 Forbidden` dengan pesan yang jelas
- `MethodArgumentNotValidException`: validasi gagal, response `400 Bad Request`
- `DomainException` dan `HttpException`: untuk error custom yang kamu definisikan sendiri

### Contoh Response JSON ketika akses ditolak

```json
{
  "code": 403,
  "message": "Access denied"
}
```

---

## 🔍 API Documentation (Swagger)

Aplikasi ini dilengkapi dengan dokumentasi API menggunakan Swagger UI.
Setelah aplikasi berjalan, Swagger dapat diakses di URL berikut:

**Sesuaikan dengan url server kalian:**

```text
http://localhost:8080/swagger-ui/index.html
```

Jika menggunakan konteks path, pastikan sesuaikan misalnya:

```text
http://localhost:8080/api/swagger-ui/index.html
```

Swagger ini membantu dalam mengeksplorasi dan menguji endpoint REST API secara langsung dari browser.

---

## 🙋 FAQ

### Apakah file `.env` wajib dibuat?

```text
Ya. File .env diperlukan untuk membaca konfigurasi runtime, terutama koneksi database.
```

### Apakah bisa generate beberapa entity sekaligus?

```text
Belum. Saat ini generator hanya mendukung satu file JSON per eksekusi.
```

### Apakah mendukung relasi antar entity?

```text
Tergantung kemampuan generator yang Anda gunakan.
```

---
