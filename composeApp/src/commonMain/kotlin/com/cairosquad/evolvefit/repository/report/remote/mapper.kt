package com.cairosquad.evolvefit.repository.report.remote

import com.cairosquad.evolvefit.domain.entity.Report
import com.cairosquad.evolvefit.domain.model.FocusArea
import com.cairosquad.evolvefit.domain.model.WeekDay
import com.cairosquad.evolvefit.repository.report.remote.dto.NutritionReportDto
import com.cairosquad.evolvefit.repository.report.remote.dto.WorkoutReportDto

fun reportDtoToReport(
    nutritionReport: NutritionReportDto,
    workoutReport: WorkoutReportDto
): Report {
    return Report(
        takenCaloriesInKcal = nutritionReport.caloriesConsumed,
        expectedCalories = nutritionReport.totalCalories,
        waterTakenInLiter = nutritionReport.waterConsumed.toFloat(),
        timeSpentInSeconds = workoutReport.totalTimeSpentSeconds,
        totalWorkouts = workoutReport.totalWorkouts,
        focusedAreas = workoutReport.topFocusAreas.mapNotNull { it.area.toFocusAreaOrNull()?.to(it.percentage) },
        timeSpentPerWeek = workoutReport.totalTimeSpentByDay.mapNotNull { dto ->
            safeWeekDay(dto.day)?.to(dto.timeInSeconds)
        },
        workoutsPerWeek = workoutReport.workoutsByDay.mapNotNull { dto ->
            safeWeekDay(dto.day)?.to(dto.workoutsCount)
        }
    )
}

private fun safeWeekDay(value: String): WeekDay? =
    runCatching { WeekDay.valueOf(value.uppercase()) }.getOrNull()

private fun String.toFocusAreaOrNull(): FocusArea? {
    return when (this.uppercase()) {
        "CHEST" -> FocusArea.CHEST
        "BACK", "LOWER_BACK" -> FocusArea.BACK
        "LEGS", "GLUTES", "QUADS", "CALVES", "INNER_THIGHS", "HAMSTRINGS" -> FocusArea.LEGS
        "SHOULDERS" -> FocusArea.SHOULDERS
        "ARMS", "TRICEPS", "BICEPS" -> FocusArea.ARMS
        "CORE", "ABS", "LOWER_ABS", "OBLIQUES" -> FocusArea.CORE
        else -> runCatching { FocusArea.valueOf(this.uppercase()) }.getOrNull()
    }
}