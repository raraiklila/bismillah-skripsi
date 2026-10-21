package com.cairosquad.evolvefit.repository.mock

import com.cairosquad.evolvefit.repository.home.data_source.remote.HomeRemoteDataSource
import com.cairosquad.evolvefit.repository.home.data_source.remote.dto.WeeklyProgressResponse
import com.cairosquad.evolvefit.repository.workout.remote.WorkoutRemoteDataSource
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

class MockHomeRemoteDataSource(
    private val workoutRemoteDataSource: WorkoutRemoteDataSource,
) : HomeRemoteDataSource {

    private val manuallyToggledDays = mutableMapOf<Int, Boolean>()

    override suspend fun getWeeklyProgress(
        startDate: String,
        endDate: String
    ): WeeklyProgressResponse {
        val history = workoutRemoteDataSource.getWorkoutHistory()
        val historyDates = history.mapNotNull { item ->
            try {
                item.createdAt.substringBefore("T")
            } catch (_: Exception) {
                null
            }
        }.toSet()

        val start = try {
            LocalDate.parse(startDate.substringBefore("T"))
        } catch (_: Exception) {
            val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            today.minus(today.dayOfWeek.ordinal, DateTimeUnit.DAY)
        }

        val workoutDatesList = mutableListOf<String>()
        var completedCount = 0

        for (dayIndex in 1..7) {
            val dateForDay = start.plus(dayIndex - 1, DateTimeUnit.DAY).toString()
            val isFromHistory = dateForDay in historyDates
            val isCompleted = manuallyToggledDays[dayIndex] ?: isFromHistory
            if (isCompleted) {
                workoutDatesList.add(dateForDay)
                completedCount++
            }
        }

        val activityPct = completedCount.toFloat() / 7f

        return WeeklyProgressResponse(
            workoutDates = workoutDatesList,
            activityPercentage = activityPct
        )
    }

    override suspend fun toggleDayProgress(day: Int, isCompleted: Boolean) {
        manuallyToggledDays[day] = isCompleted
    }
}
