package com.cairosquad.evolvefit.repository.mock

import com.cairosquad.evolvefit.repository.home.data_source.remote.HomeRemoteDataSource
import com.cairosquad.evolvefit.repository.home.data_source.remote.dto.WeeklyProgressResponse

class MockHomeRemoteDataSource : HomeRemoteDataSource {
    override suspend fun getWeeklyProgress(
        startDate: String,
        endDate: String
    ): WeeklyProgressResponse {
        return WeeklyProgressResponse(
            workoutDates = listOf(
                startDate,
            ),
            activityPercentage = 0.6f
        )
    }
}
