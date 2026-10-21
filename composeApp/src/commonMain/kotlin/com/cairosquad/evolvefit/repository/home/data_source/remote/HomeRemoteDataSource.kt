package com.cairosquad.evolvefit.repository.home.data_source.remote

import com.cairosquad.evolvefit.repository.home.data_source.remote.dto.WeeklyProgressResponse

interface HomeRemoteDataSource {
    suspend fun getWeeklyProgress(
        startDate: String,
        endDate: String,
    ): WeeklyProgressResponse

    suspend fun toggleDayProgress(
        day: Int,
        isCompleted: Boolean,
    ) {}
}