# 📄 Laporan Rekapitulasi Perubahan Kode (EvolveFit - Before)

Dokumen ini berisi daftar jujur, transparan, dan detail mengenai seluruh perubahan kode yang dilakukan pada cabang/repositori **`evolve-before`** dari awal sesi hingga saat ini.

---

## 1. ⚙️ Konfigurasi Build, Gradle, & Dependency Injection (DI)

### 🔹 `composeApp/build.gradle.kts` [MODIFIED]
- **Perubahan:** Menonaktifkan sementara (*comment out*) plugin Firebase:
  - `alias(libs.plugins.googleFirebaseAppdistribution)`
  - `alias(libs.plugins.googleGmsGoogleServices)`
- **Alasan:** Menghindari kegagalan *build* lokal akibat ketergantungan berkas `google-services.json` yang membutuhkan koneksi backend remote.

### 🔹 `gradle.properties` [MODIFIED]
- **Perubahan:** Menambahkan konfigurasi `kotlin.native.ignoreDisabledTargets=true`.
- **Alasan:** Menonaktifkan target kompilasi Apple/iOS di lingkungan OS Windows agar tidak memunculkan peringatan kegagalan target non-Windows.

### 🔹 `local.properties` [MODIFIED]
- **Perubahan:** Menambahkan konfigurasi `sdk.dir=C:\\Users\\ASUS\\AppData\\Local\\Android\\Sdk`.
- **Alasan:** Memastikan Gradle dapat menemukan lokasi Android SDK lokal di komputer pengguna untuk proses kompilasi dan *install debug*.

### 🔹 `composeApp/src/commonMain/kotlin/com/cairosquad/evolvefit/di/appModules.kt` [MODIFIED]
- **Perubahan:** Mengganti pendaftaran modul Koin dari `remoteModule` ke `mockRemoteModule`.
- **Alasan:** Mengalihkan seluruh *network calls* aplikasi dari backend remote (`evolve-fit-dev.the-chance.net` yang sedang nonaktif) ke data *mock* lokal di memori (*offline mode*).

### 🔹 `composeApp/src/commonMain/kotlin/com/cairosquad/evolvefit/di/mockRemoteModule.kt` [NEW]
- **Perubahan:** Membuat modul Koin baru yang meng-*bind* seluruh interface `RemoteDataSource` ke implementasi *Mock*-nya, termasuk menyuntikkan `ExerciseRemoteDataSource` ke dalam `MockWorkoutRemoteDataSource`.

---

## 2. 📦 Modul Mock Data Source Baru (`composeApp/src/commonMain/kotlin/com/cairosquad/evolvefit/repository/mock/`)

Seluruh file di direktori ini adalah file **BARU** yang dibuat untuk mensimulasikan API backend secara mandiri (*offline*):

### 🔹 `MockNutritionRemoteDataSource.kt` [NEW]
- **Fitur & Logic:**
  - Memiliki *in-memory state* untuk `consumedCalories` dan `consumedWaterLiters`.
  - `saveConsumedMeal()`: Menambah kalori dan menyimpan riwayat makanan yang dikonsumsi dengan format timestamp ISO 8601 (`YYYY-MM-DDTHH:MM:SS`) untuk mencegah *crash* pada *parsing* tanggal.
  - `saveConsumedWater()`: Menambah akumulasi konsumsi air harian dalam liter.
  - `getDailyCalorieSummary()` & `getDailyWaterSummary()`: Mengembalikan total dan target harian yang dinamis.
  - `getMealHistory()` & `getConsumedMealsByDate()`: Mengembalikan daftar makanan yang baru ditambahkan.
  - Memperbarui URL gambar "Oatmeal with Berries" dengan foto semangkuk oatmeal dan buah beri segar yang sesuai (menggantikan tautan lama yang menampilkan foto buku).

### 🔹 `MockWorkoutRemoteDataSource.kt` [NEW]
- **Fitur & Logic:**
  - Memiliki *in-memory state* untuk daftar `mockWorkouts`, `favoriteWorkouts`, dan `workoutHistory`.
  - **4 Preset Workout Unik:**
    1. **Full Body Strength**
    2. **Core Crusher**
    3. **Leg Day Blast**
    4. **Upper Body Power**
  - `createWorkout()`:
    - Me-resolve latihan kustom yang dipilih pengguna melalui `exerciseRemoteDataSource`.
    - Menghitung estimasi durasi workout secara otomatis dari akumulasi repetisi dan durasi latihan yang dipilih.
    - Menentukan target `focusArea` secara otomatis berdasarkan kategori latihan di dalamnya.
    - **Penempatan Workout Baru:** Menambahkan workout kustom ke bagian **bawah (akhir daftar)** dengan `mockWorkouts.add(...)` (bukan di index 0) sesuai perilaku baseline versi *before*.
  - `submitPlayedWorkout()`: Menyimpan latihan yang telah diselesaikan ke dalam `workoutHistory` dengan timestamp ISO 8601 yang valid.
  - `addFavoriteWorkout()` & `deleteFavoriteWorkout()`: Mengelola daftar favorit secara dinamis.

### 🔹 `MockExerciseRemoteDataSource.kt` [NEW]
- **Fitur & Logic:**
  - Katalog latihan bawaan dibatasi tepat **4 latihan standar** agar tampilan ringkas dan bersih sesuai desain referensi:
    1. **Push-up** (15 Reps)
    2. **Barbell Squat** (10 Reps)
    3. **Plank** (10 Second)
    4. **Dumbbell Curl** (12 Reps)
  - `createExercise()`: Menyimpan latihan kustom baru yang dibuat oleh pengguna ke dalam daftar latihan *in-memory*.

### 🔹 `MockProfileRemoteDataSource.kt` [NEW]
- Menyimpan dan memperbarui data profil pengguna (nama, gender, berat badan, tinggi badan, target kebugaran) secara *in-memory*.

### 🔹 `MockAuthenticationRemoteDataSource.kt` [NEW]
- Mensimulasikan proses login dan registrasi akun pengguna tanpa memerlukan koneksi server remote.

### 🔹 `MockReportRemoteDataSource.kt` [NEW]
- Menyediakan data statistik grafik mingguan untuk laporan durasi latihan, jumlah latihan, dan konsumsi nutrisi harian.

### 🔹 `MockHomeRemoteDataSource.kt` [NEW]
- Menyediakan ringkasan progres mingguan untuk halaman Home.

### 🔹 `MockEquipmentRemoteDataSource.kt` [NEW]
- Menyediakan daftar peralatan gym standar.

---

## 3. 🏋️ Perbaikan Pembuatan Custom Workout & Custom Exercise

### 🔹 `composeApp/src/commonMain/kotlin/com/cairosquad/evolvefit/ui/navigation/NavigationHost.kt` [MODIFIED]
- **Perubahan:** Menambahkan mekanisme callback `onExerciseCreationSuccessCallback` pada saat navigasi dari `CreateWorkout` ke `CreateExercise`.
- **Alasan:** Memastikan saat pengguna selesai membuat gerakan (*exercise*) baru dan kembali ke layar pemilihan latihan, daftar latihan langsung di-refresh (`loadExercises()`) secara otomatis.

### 🔹 `composeApp/src/commonMain/kotlin/com/cairosquad/evolvefit/viewmodel/create_workout/CreateWorkoutViewModel.kt` [MODIFIED]
- **Perubahan:**
  - Menentukan daftar ID latihan dasar `baseExerciseIds = setOf("ex-1", "ex-2", "ex-3", "ex-4")`.
  - Memisahkan latihan kustom buatan pengguna ke dalam `newlyAddExercises` dan otomatis mencentangnya (*checked*).
  - Memastikan daftar *"All Exercises"* tetap konsisten hanya berisi 4 latihan standar bawaan.
- **Alasan:** Agar latihan kustom yang baru ditambahkan muncul rapi di atas label *"All Exercises"* dan langsung terpilih untuk dimasukkan ke workout.

### 🔹 `composeApp/src/commonMain/kotlin/com/cairosquad/evolvefit/ui/screen/createWorkout/content/AllExercisesContent.kt` [MODIFIED]
- **Perubahan:**
  - Menampilkan latihan kustom baru (`visibleNewlyAdded`) tepat di atas judul *"All Exercises"*.
  - Menampilkan 4 latihan standar di bawah judul *"All Exercises"*.
  - Menambahkan penyaringan (*search filter*) yang turut memfilter latihan baru jika pengguna mengetik nama latihan di kotak pencarian.

### 🔹 `composeApp/src/commonMain/kotlin/com/cairosquad/evolvefit/viewmodel/create_workout/mapper.kt` [MODIFIED]
- **Perubahan:**
  - Memperbaiki `toDomainWorkout(exercises)` agar menerima daftar `selectedExercises` (sebelumnya selalu dikirimkan list kosong `emptyList()`).
  - Menghitung `estimatedTimeInSeconds` dari durasi/repetisi latihan yang dipilih.
- **Alasan:** Mencegah workout yang baru dibuat kehilangan daftar latihan yang sudah dicentang oleh pengguna.

### 🔹 `composeApp/src/commonMain/kotlin/com/cairosquad/evolvefit/viewmodel/exercise/mapper.kt` & `CreateExerciseViewModel.kt` [MODIFIED]
- **Perubahan:** Memperbaiki pemetaan tipe pengukuran (*Reps* vs *Duration/Time*) dan penanganan upload gambar latihan kustom.

---

## 4. 📊 Penyesuaian Layar Laporan (Report Screen)

### 🔹 `composeApp/.../ui/screen/report/content/componant/animatedMeter/chartComponent/ChartCanvas.kt` [MODIFIED]
- **Perubahan:** Menghapus penggambaran lingkaran penanda titik puncak (*outer ring*, *inner core circle*, dan garis vertikal putus-putus) pada grafik garis *"Time Spent Training"*.
- **Alasan:** Menyesuaikan visual grafik kurva dan *gradient area fill* agar tampil bersih tanpa bulatan penanda sesuai permintaan baseline versi *before*.

### 🔹 `composeApp/src/commonMain/kotlin/com/cairosquad/evolvefit/viewmodel/report/mapper.kt` [MODIFIED]
- **Perubahan:** Memperbaiki konversi persentase pada kartu *"Most Trained Muscles"*.
  - Data dari *mock remote* mengembalikan pecahan desimal (misalnya `0.35`, `0.30`, `0.20`, `0.15`).
  - Sebelumnya nilai tersebut dibagi ulang dengan 100 (`percentage / 100f`), menghasilkan `0.0035` sehingga terbaca `0%` dengan *progress bar* kosong.
  - Ditambahkan normalisasi adaptif: `if (p > 1.0f) p / 100f else p`.
- **Alasan:** Menampilkan angka persentase yang benar (35%, 30%, 20%, 15%) dan mengisi *progress bar* otot.

### 🔹 `composeApp/.../ui/screen/report/content/componant/cards/MusclesCard.kt` [MODIFIED]
- **Perubahan:**
  - Menambahkan `Modifier.weight(1f)` pada `Column` kartu otot di dalam `Row`.
  - Mengamankan nilai animasi pecahan dengan `animatePercentage.coerceIn(0f, 1f)`.
  - Menyesuaikan *padding* bawah kartu agar tidak berlebih pada item terakhir (`it < musclesName.size - 1`).
- **Alasan:** Memastikan *progress bar* kartu otot dapat mengisi lebar kartu secara proporsional dan beranimasi naik dengan lancar.

---

## 5. 🔄 Lifecycle & Pembaruan Tampilan Otomatis (Auto-Refresh)

### 🔹 `composeApp/src/commonMain/kotlin/com/cairosquad/evolvefit/ui/screen/home/HomeScreen.kt` & `HomeViewModel.kt` [MODIFIED]
- **Perubahan:** Menambahkan method `refreshData()` pada `HomeViewModel` dan memanggilnya via `LaunchedEffect(Unit)` pada `HomeScreen`.
- **Alasan:** Memperbarui ringkasan nutrisi (kalori & air) saat pengguna kembali ke tab Home setelah mencatat makanan/minuman di tab lain.

### 🔹 `composeApp/src/commonMain/kotlin/com/cairosquad/evolvefit/ui/screen/workout/WorkoutsScreen.kt` & `WorkoutViewModel.kt` [MODIFIED]
- **Perubahan:** Menambahkan pemantau siklus hidup `LifecycleEventEffect(Lifecycle.Event.ON_RESUME) { viewModel.reloadWorkouts() }`.
- **Alasan:** Memastikan daftar latihan pada tab Workouts langsung memuat ulang data saat pengguna kembali dari layar pembuatan latihan/workout tanpa perlu me-restart aplikasi.

---

## 6. 🏠 Progres Mingguan Berfungsi & Responsif di Home Screen (`HomeProgressBox`)

### 🔹 `MockProfileRemoteDataSource.kt` [MODIFIED]
- **Perubahan:** Mengganti target kebugaran default akun dari `"BUILD_MUSCLE"` (yang sebelumnya jatuh ke fallback "Staying in shape") menjadi `"LOSE_WEIGHT"`.
- **Alasan:** Memastikan label target utama (*The Goal*) di Home menampilkan teks *"Weight loss"* sesuai permintaan referensi.

### 🔹 `HomeProgressBox.kt` [MODIFIED]
- **Perubahan:**
  1. **Tampilan Hari 1–7:** Mengganti baris lingkaran hari menjadi tepat 7 lingkaran hari (`1` sampai `7`) yang tersusun rapi menggunakan `Row(horizontalArrangement = Arrangement.SpaceBetween)`.
  2. **Interaktif & Fungsional (Bukan Dummy):** Menambahkan `clickable` pada tiap lingkaran hari (`DayBox`) sehingga pengguna dapat mencentang/menonaktifkan hari latihan langsung dari layar Home.
  3. **Penataan Kolom Statistik (*The Goal*, *Current weight*, *Activity*):**
     - Mengubah bobot (*weight*) kolom menjadi `1.15f` (The Goal), `1.15f` (Current weight), dan `0.85f` (Activity).
     - Menghapus padding berlebih (`16.dp`) yang sebelumnya menekan kolom *The Goal*.
     - Mengubah teks nilai target kebugaran menjadi `maxLines = 2`, `softWrap = true`, dan menonaktifkan pemotongan elipsis (`...`).
- **Alasan:** Mencegah teks *"Weight loss"* terpotong titik-titik (*ellipsis/nabrak*) sehingga dapat dibaca utuh oleh pengguna, serta membuat kotak progres mingguan berfungsi dinamis saat didemokan.

### 🔹 `MockHomeRemoteDataSource.kt` & Aliran Data Progres [MODIFIED]
- **Perubahan:**
  - Menghubungkan `MockHomeRemoteDataSource` dengan riwayat latihan (`WorkoutRemoteDataSource`).
  - Menghitung persentase aktivitas mingguan (*Activity %*) secara matematis berdasarkan akumulasi hari yang telah selesai (`completedDays / 7 * 100%`).
  - Menyimpan status hari yang dicentang secara manual (`toggleDayProgress`) agar tetap tersimpan selama sesi aplikasi.
  - Memperbarui `refreshData()` pada `HomeViewModel` untuk memuat ulang `loadProgress()` dan `loadUserInfo()`.
- **Alasan:** Memastikan kartu progres mingguan benar-benar dinamis (*real-time*), terintegrasi saat latihan selesai dimainkan, dan dapat dicentang langsung dari beranda.

