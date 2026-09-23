package com.cairosquad.evolvefit.repository.mock

import com.cairosquad.evolvefit.repository.profile.remote.ProfileRemoteDataSource
import com.cairosquad.evolvefit.repository.profile.remote.dto.ProfileRequest
import com.cairosquad.evolvefit.repository.profile.remote.dto.ProfileResponse

class MockProfileRemoteDataSource : ProfileRemoteDataSource {

    private var storedProfile = ProfileResponse(
        name = "EvolveFit User",
        email = "user@evolvefit.com",
        birthDate = "2000-01-01",
        gender = "MALE",
        imageUrl = "",
        measurementType = "METRIC",
        height = 175.0,
        weight = 70.0,
        goal = "BUILD_MUSCLE",
        gymEquipments = emptyList(),
        workoutDays = listOf("MONDAY", "WEDNESDAY", "FRIDAY")
    )

    override suspend fun getProfile(): ProfileResponse {
        return storedProfile
    }

    override suspend fun editProfile(profileRequest: ProfileRequest): ProfileResponse {
        storedProfile = storedProfile.copy(
            name = profileRequest.name.ifEmpty { storedProfile.name },
            gender = profileRequest.gender.ifEmpty { storedProfile.gender },
            height = if (profileRequest.height != 0.0) profileRequest.height else storedProfile.height,
            weight = if (profileRequest.weight != 0.0) profileRequest.weight else storedProfile.weight,
            birthDate = profileRequest.birthDate.ifEmpty { storedProfile.birthDate },
            goal = profileRequest.goal.ifEmpty { storedProfile.goal },
        )
        return storedProfile
    }

    override suspend fun uploadProfileImage(fileBytes: ByteArray, fileName: String): String {
        return "https://images.unsplash.com/photo-1633332755192-727a05c4013d?w=400"
    }
}
