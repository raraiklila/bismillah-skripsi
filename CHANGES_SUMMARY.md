# 📄 Laporan Rekapitulasi Perubahan Kode (EvolveFit)

Dokumen ini berisi daftar jujur, transparan, dan detail mengenai seluruh perubahan kode yang dilakukan dari awal sesi hingga saat ini.

---

## 1. ⚙️ Konfigurasi Build & Dependency Injection (DI)

### 🔹 `composeApp/build.gradle.kts` [MODIFIED]
- **Perubahan:** Menonaktifkan sementara (comment out) plugin Firebase:
  - `alias(libs.plugins.googleFirebaseAppdistribution)`
  - `alias(libs.plugins.googleGmsGoogleServices)`
- **Alasan:** Menghindari kegagalan build lokal akibat berkas konfigurasi Google Services yang membutuhkan koneksi backend.

### 🔹 `composeApp/src/commonMain/kotlin/com/cairosquad/evolvefit/di/appModules.kt` [MODIFIED]
- **Perubahan:** Mengganti pendaftaran modul Koin dari `remoteModule` ke `mockRemoteModule`.
- **Alasan:** Mengalihkan seluruh *network calls* aplikasi dari backend remote (`evolve-fit-dev.the-chance.net` yang sedang offline) ke data mock lokal di memori (*offline mode*).

### 🔹 `composeApp/src/commonMain/kotlin/com/cairosquad/evolvefit/di/mockRemoteModule.kt` [NEW]
- **Perubahan:** Membuat modul Koin baru yang meng-bind seluruh interface `RemoteDataSource` ke implementasi Mock-nya.

---

## 2. 📦 Modul Mock Data Source Baru (`composeApp/src/commonMain/kotlin/com/cairosquad/evolvefit/repository/mock/`)

Seluruh file di direktori ini adalah file **BARU** yang dibuat untuk mensimulasikan API backend:

### 🔹 `MockNutritionRemoteDataSource.kt` [NEW]
- **Fitur & Logic:**
  - Memiliki *in-memory state* untuk `consumedCalories` dan `consumedWaterLiters`.
  - `saveConsumedMeal()`: Menambah kalori dan menyimpan riwayat makanan yang dikonsumsi dengan format timestamp ISO 8601 (`YYYY-MM-DDTHH:MM:SS`) untuk mencegah crash pada parsing tanggal.
  - `saveConsumedWater()`: Menambah akumulasi konsumsi air harian dalam liter.
  - `getDailyCalorieSummary()` & `getDailyWaterSummary()`: Mengembalikan total dan target harian yang dinamis.
  - `getMealHistory()` & `getConsumedMealsByDate()`: Mengembalikan daftar makanan yang baru ditambahkan.

### 🔹 `MockWorkoutRemoteDataSource.kt` [NEW]
- **Fitur & Logic:**
  - Memiliki *in-memory state* untuk daftar `mockWorkouts`, `favoriteWorkouts`, dan `workoutHistory`.
  - **4 Preset Workout Unik:**
    1. **Full Body Strength** (8 Gerakan: Push-up 20 reps, Squat 20 reps, Dumbbell Row 15 reps, Shoulder Press 15 reps, Plank 60s, Bicep Curl 15 reps, Jumping Jacks 30 reps, Mountain Climbers 45s).
    2. **Core Crusher** (6 Gerakan: Plank Hold 60s, Mountain Climbers 45s, Abdominal Crunches 25 reps, Leg Raises 20 reps, Russian Twists 30 reps, Bicycle Crunches 20 reps).
    3. **Leg Day Blast** (6 Gerakan: Squat 25 reps, Walking Lunges 20 reps, Calf Raises 30 reps, Wall Sit 45s, Sumo Squat 20 reps, Glute Bridges 25 reps).
    4. **Upper Body Power** (5 Gerakan: Push-up 25 reps, Dumbbell Row 15 reps, Shoulder Press 15 reps, Bicep Curl 15 reps, Tricep Dips 20 reps).
  - `submitPlayedWorkout()`: Menyimpan latihan yang telah diselesaikan ke dalam `workoutHistory` dengan timestamp ISO 8601 yang valid.
  - `addFavoriteWorkout()` & `deleteFavoriteWorkout()`: Mengelola daftar favorit secara dinamis.
  - `createWorkout()`: Menambahkan workout kustom buatan user ke dalam daftar workout.

### 🔹 `MockProfileRemoteDataSource.kt` [NEW]
- Menyimpan dan memperbarui data profil user (nama, gender, berat badan, tinggi badan, target fitness) secara *in-memory*.

### 🔹 `MockAuthenticationRemoteDataSource.kt` [NEW]
- Mensimulasikan proses login dan registrasi akun user.

### 🔹 `MockReportRemoteDataSource.kt` [NEW]
- Menyediakan data statistik grafik mingguan untuk laporan workout dan konsumsi nutrisi.

### 🔹 `MockHomeRemoteDataSource.kt` [NEW]
- Menyediakan progress mingguan untuk halaman Home.

### 🔹 `MockExerciseRemoteDataSource.kt` [NEW]
- Menyediakan katalog gerakan latihan.

### 🔹 `MockEquipmentRemoteDataSource.kt` [NEW]
- Menyediakan daftar peralatan gym.

---

## 3. 🖥️ Perubahan pada Halaman & ViewModel Home

### 🔹 `composeApp/src/commonMain/kotlin/com/cairosquad/evolvefit/viewmodel/home/HomeViewModel.kt` [MODIFIED]
- **Perubahan:** Menambahkan method publik `fun refreshData()`.
- **Alasan:** Memungkinkan pemanggilan ulang `loadNutrition()` saat user kembali ke halaman Home dari tab lain.

### 🔹 `composeApp/src/commonMain/kotlin/com/cairosquad/evolvefit/ui/screen/home/HomeScreen.kt` [MODIFIED]
- **Perubahan:** Menambahkan `LaunchedEffect(Unit) { homeViewModel.refreshData() }`.
- **Alasan:** Memicu pembaruan angka kalori dan liter air pada tampilan Home setiap kali tab Home aktif/dibuka kembali.

---

## 🎨 Catatan Mengenai UI & Design System

> **TIDAK ADA PERUBAHAN SAMA SEKALI PADA UI / DESIGN SYSTEM.**
> 
> Seluruh warna, font, layout, komponen Compose, ukuran margin/padding, dan struktur navigasi dasar tidak mengalami perubahan apa pun. Semua perubahan di atas murni dilakukan pada **layer data mock backend** dan pemanggilan **refresh state**.
