package com.cairosquad.evolvefit.repository.mock

import com.cairosquad.evolvefit.repository.home.data_source.remote.HomeRemoteDataSource
import com.cairosquad.evolvefit.repository.home.data_source.remote.dto.WeeklyProgressResponse
import com.cairosquad.evolvefit.repository.workout.remote.WorkoutRemoteDataSource

class MockHomeRemoteDataSource(
    private val workoutRemoteDataSource: WorkoutRemoteDataSource? = null
) : HomeRemoteDataSource {
    override suspend fun getWeeklyProgress(
        startDate: String,
        endDate: String
    ): WeeklyProgressResponse {
        val history = workoutRemoteDataSource?.getWorkoutHistory().orEmpty()
        val historyDates = history.map { it.createdAt.substringBefore("T") }

        val startIso = startDate.substringBefore("T")
        val endIso = endDate.substringBefore("T")
        val thisWeekWorkouts = historyDates.filter { date ->
            date >= startIso && date <= endIso
        }.distinct()

        val activity = if (thisWeekWorkouts.isNotEmpty()) {
            (thisWeekWorkouts.size.toFloat() / 7f).coerceIn(0f, 1f)
        } else {
            0.0f
        }

        return WeeklyProgressResponse(
            workoutDates = thisWeekWorkouts,
            activityPercentage = activity
        )
    }
}
