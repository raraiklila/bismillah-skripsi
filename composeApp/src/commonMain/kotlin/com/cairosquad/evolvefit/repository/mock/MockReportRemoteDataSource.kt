package com.cairosquad.evolvefit.repository.mock

import com.cairosquad.evolvefit.repository.nutrition.remote.NutritionRemoteDataSource
import com.cairosquad.evolvefit.repository.report.remote.ReportRemoteDataSource
import com.cairosquad.evolvefit.repository.report.remote.dto.NutritionReportDto
import com.cairosquad.evolvefit.repository.report.remote.dto.TopFocusArea
import com.cairosquad.evolvefit.repository.report.remote.dto.TotalTimeSpentByDay
import com.cairosquad.evolvefit.repository.report.remote.dto.WorkoutReportDto
import com.cairosquad.evolvefit.repository.report.remote.dto.WorkoutsByDay
import com.cairosquad.evolvefit.repository.workout.remote.WorkoutRemoteDataSource
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class MockReportRemoteDataSource(
    private val workoutRemoteDataSource: WorkoutRemoteDataSource,
    private val nutritionRemoteDataSource: NutritionRemoteDataSource,
) : ReportRemoteDataSource {

    override suspend fun getWorkoutReport(
        startDate: String,
        endDate: String
    ): WorkoutReportDto {
        val history = workoutRemoteDataSource.getWorkoutHistory()
        val totalWorkouts = history.size
        val totalTimeSpentSeconds = history.sumOf { it.durationSeconds }

        val timeByDayMap = mutableMapOf<String, Long>()
        val countByDayMap = mutableMapOf<String, Int>()

        history.forEach { item ->
            val dayName = getDayNameFromCreatedAt(item.createdAt)
            timeByDayMap[dayName] = (timeByDayMap[dayName] ?: 0L) + item.durationSeconds
            countByDayMap[dayName] = (countByDayMap[dayName] ?: 0) + 1
        }

        val totalTimeSpentByDay = timeByDayMap.map { (day, time) ->
            TotalTimeSpentByDay(day = day, timeInSeconds = time)
        }
        val workoutsByDay = countByDayMap.map { (day, count) ->
            WorkoutsByDay(day = day, workoutsCount = count)
        }

        val allWorkouts = (workoutRemoteDataSource.getSuggestedWorkouts() + workoutRemoteDataSource.getCommunityWorkouts()).distinctBy { it.name }
        val workoutMap = allWorkouts.associateBy { it.name }

        // Dynamic muscle tracking based on workouts in history
        val focusAreaCount = mutableMapOf<String, Int>()
        focusAreaCount["CHEST"] = 4
        focusAreaCount["LEGS"] = 3
        focusAreaCount["CORE"] = 2
        focusAreaCount["ARMS"] = 1

        history.forEach { item ->
            val workout = workoutMap[item.name]
            val areas = workout?.focusArea?.map { it.name }
                ?: if (item.name.contains("Core", ignoreCase = true)) listOf("CORE")
                else if (item.name.contains("Leg", ignoreCase = true)) listOf("LEGS")
                else if (item.name.contains("Upper", ignoreCase = true) || item.name.contains("Arm", ignoreCase = true)) listOf("ARMS")
                else listOf("CHEST", "CORE")

            areas.forEach { area ->
                focusAreaCount[area] = (focusAreaCount[area] ?: 0) + 3
            }
        }

        val totalAreaHits = focusAreaCount.values.sum().coerceAtLeast(1)
        val dynamicFocusAreas = focusAreaCount.map { (area, count) ->
            TopFocusArea(
                area = area,
                percentage = kotlin.math.round((count.toDouble() / totalAreaHits.toDouble()) * 100.0)
            )
        }.sortedByDescending { it.percentage }

        return WorkoutReportDto(
            topFocusAreas = dynamicFocusAreas,
            totalTimeSpentByDay = totalTimeSpentByDay,
            totalTimeSpentSeconds = totalTimeSpentSeconds,
            totalWorkouts = totalWorkouts,
            workoutsByDay = workoutsByDay
        )
    }

    override suspend fun getNutritionReport(
        startDate: String,
        endDate: String
    ): NutritionReportDto {
        val calorieSummary = nutritionRemoteDataSource.getDailyCalorieSummary()
        val waterSummary = nutritionRemoteDataSource.getDailyWaterSummary()

        // Laporan per Minggu (7 hari):
        // Target mingguan = target harian x 7 hari (2.000 kcal x 7 = 14.000 kcal)
        val weeklyTargetCalories = calorieSummary.totalCalories * 7

        // Akumulasi kalori mingguan:
        // Estimasi 6 hari sebelumnya (7.200 kcal) + kalori yang dikonsumsi hari ini (1.250 kcal -> 8.450 kcal)
        val basePastDaysCalories = 7200
        val totalWeeklyCalories = basePastDaysCalories + calorieSummary.consumedCalories

        // Akumulasi air minum mingguan:
        // Estimasi 6 hari sebelumnya (9.0 Liter) + air minum hari ini (1.5 Liter -> 10.5 Liter)
        val basePastDaysWater = 9.0
        val totalWeeklyWater = basePastDaysWater + waterSummary.consumedWater

        return NutritionReportDto(
            caloriesConsumed = totalWeeklyCalories,
            totalCalories = weeklyTargetCalories,
            waterConsumed = totalWeeklyWater
        )
    }

    private fun getDayNameFromCreatedAt(createdAt: String): String {
        return runCatching {
            val instant = Instant.parse(createdAt)
            val ldt = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            ldt.dayOfWeek.name
        }.getOrElse {
            runCatching {
                val date = LocalDate.parse(createdAt.take(10))
                date.dayOfWeek.name
            }.getOrDefault("MONDAY")
        }
    }
}
