package com.cairosquad.evolvefit.repository.home.data_source.remote.dto

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

fun createWorkoutDatesMap(
    startDate: LocalDate,
    endDate: LocalDate,
    workoutDates: List<String>
): Map<Int, Boolean> {
    val workoutDateStrings = workoutDates.map { it.substringBefore("T") }.toSet()
    val result = mutableMapOf<Int, Boolean>()

    var current = startDate
    var dayIndex = 1
    while (current <= endDate && dayIndex <= 7) {
        val dateString = current.toString()
        val isCompleted = dateString in workoutDateStrings
        result[dayIndex] = isCompleted
        current = current.plus(1, DateTimeUnit.DAY)
        dayIndex++
    }
    while (dayIndex <= 7) {
        result[dayIndex] = false
        dayIndex++
    }
    return result
}