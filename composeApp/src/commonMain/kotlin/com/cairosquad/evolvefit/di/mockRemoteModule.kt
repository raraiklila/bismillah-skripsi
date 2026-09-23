package com.cairosquad.evolvefit.di

import com.cairosquad.evolvefit.repository.authentication.remote.AuthenticationRemoteDataSource
import com.cairosquad.evolvefit.repository.equipment.remote.EquipmentsRemoteDataSource
import com.cairosquad.evolvefit.repository.exercise.remote.ExerciseRemoteDataSource
import com.cairosquad.evolvefit.repository.home.data_source.remote.HomeRemoteDataSource
import com.cairosquad.evolvefit.repository.mock.MockAuthenticationRemoteDataSource
import com.cairosquad.evolvefit.repository.mock.MockEquipmentRemoteDataSource
import com.cairosquad.evolvefit.repository.mock.MockExerciseRemoteDataSource
import com.cairosquad.evolvefit.repository.mock.MockHomeRemoteDataSource
import com.cairosquad.evolvefit.repository.mock.MockNutritionRemoteDataSource
import com.cairosquad.evolvefit.repository.mock.MockProfileRemoteDataSource
import com.cairosquad.evolvefit.repository.mock.MockReportRemoteDataSource
import com.cairosquad.evolvefit.repository.mock.MockWorkoutRemoteDataSource
import com.cairosquad.evolvefit.repository.nutrition.remote.NutritionRemoteDataSource
import com.cairosquad.evolvefit.repository.profile.remote.ProfileRemoteDataSource
import com.cairosquad.evolvefit.repository.report.remote.ReportRemoteDataSource
import com.cairosquad.evolvefit.repository.workout.remote.WorkoutRemoteDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Mock remote module — menggantikan remoteModule saat backend tidak tersedia.
 * Semua datasource di-replace dengan implementasi dummy / lokal.
 *
 * Untuk kembali ke backend asli, ganti [mockRemoteModule] dengan [remoteModule]
 * di dalam [appModules].
 */
val mockRemoteModule = module {
    singleOf(::MockAuthenticationRemoteDataSource).bind(AuthenticationRemoteDataSource::class)
    singleOf(::MockExerciseRemoteDataSource).bind(ExerciseRemoteDataSource::class)
    singleOf(::MockEquipmentRemoteDataSource).bind(EquipmentsRemoteDataSource::class)
    singleOf(::MockNutritionRemoteDataSource).bind(NutritionRemoteDataSource::class)
    singleOf(::MockWorkoutRemoteDataSource).bind(WorkoutRemoteDataSource::class)
    singleOf(::MockReportRemoteDataSource).bind(ReportRemoteDataSource::class)
    singleOf(::MockProfileRemoteDataSource).bind(ProfileRemoteDataSource::class)
    singleOf(::MockHomeRemoteDataSource).bind(HomeRemoteDataSource::class)
}
