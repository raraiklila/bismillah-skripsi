package com.cairosquad.evolvefit.repository.mock

import com.cairosquad.evolvefit.repository.authentication.remote.AuthenticationRemoteDataSource
import com.cairosquad.evolvefit.repository.authentication.remote.dto.AuthResponse
import com.cairosquad.evolvefit.repository.authentication.remote.dto.RegisterRequest

class MockAuthenticationRemoteDataSource : AuthenticationRemoteDataSource {

    override suspend fun login(email: String, password: String): AuthResponse {
        return AuthResponse(
            accessToken = "mock-access-token-12345",
            refreshToken = "mock-refresh-token-12345"
        )
    }

    override suspend fun register(request: RegisterRequest): AuthResponse {
        return AuthResponse(
            accessToken = "mock-access-token-12345",
            refreshToken = "mock-refresh-token-12345"
        )
    }

    override suspend fun logout() {
        // No-op for mock
    }
}
