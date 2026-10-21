package com.cairosquad.evolvefit.repository.home.data_source.remote.dto

import com.cairosquad.evolvefit.repository.utils.localDateTimeToLocalDate
import com.cairosquad.evolvefit.repository.utils.parseIsoDateStringToLocalDateTime
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

fun createWorkoutDatesMap(
    startDate: LocalDate,
    endDate: LocalDate,
    workoutDates: List<String>
): Map<Int, Boolean> {
    val workoutSet = workoutDates.mapNotNull {
        try {
            it.substringBefore("T")
        } catch (_: Exception) {
            null
        }
    }.toSet()
    val result = mutableMapOf<Int, Boolean>()

    var dayIndex = 1
    var current = startDate
    while (current <= endDate && dayIndex <= 7) {
        result[dayIndex] = current.toString() in workoutSet
        current = current.plus(1, DateTimeUnit.DAY)
        dayIndex++
    }
    for (i in 1..7) {
        if (!result.containsKey(i)) {
            result[i] = false
        }
    }
    return result
}