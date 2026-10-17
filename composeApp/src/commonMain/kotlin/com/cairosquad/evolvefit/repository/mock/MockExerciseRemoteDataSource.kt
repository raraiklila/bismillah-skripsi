package com.cairosquad.evolvefit.repository.mock

import com.cairosquad.evolvefit.repository.exercise.remote.ExerciseRemoteDataSource
import com.cairosquad.evolvefit.repository.exercise.remote.dto.ExerciseDto
import com.cairosquad.evolvefit.repository.exercise.remote.dto.ExerciseResponseDto
import com.cairosquad.evolvefit.repository.equipment.remote.dto.GymEquipmentDto

class MockExerciseRemoteDataSource : ExerciseRemoteDataSource {

    private val mockExercises = mutableListOf(
        ExerciseResponseDto(
            id = "ex-1",
            name = "Push-up",
            instructions = listOf(
                "Start in a plank position",
                "Lower your body until chest nearly touches floor",
                "Push yourself back up"
            ),
            images = listOf("https://images.unsplash.com/photo-1571019614242-c5c5dee9f50b?w=400"),
            gymEquipments = emptyList(),
            focusArea = listOf("CHEST", "ARMS"),
            exerciseType = "REPS",
            reps = 15
        ),
        ExerciseResponseDto(
            id = "ex-2",
            name = "Barbell Squat",
            instructions = listOf(
                "Place barbell across upper back",
                "Stand with feet shoulder-width apart",
                "Lower hips until thighs are parallel to floor",
                "Return to standing position"
            ),
            images = listOf("https://images.unsplash.com/photo-1574680096145-d05b474e2155?w=400"),
            gymEquipments = listOf(GymEquipmentDto(id = 1, name = "Barbell")),
            focusArea = listOf("LEGS"),
            exerciseType = "REPS",
            reps = 10
        ),
        ExerciseResponseDto(
            id = "ex-3",
            name = "Plank",
            instructions = listOf(
                "Start in forearm plank position",
                "Keep body in straight line",
                "Hold the position"
            ),
            images = listOf("https://images.unsplash.com/photo-1536922246289-88c42f957773?w=400"),
            gymEquipments = emptyList(),
            focusArea = listOf("CORE"),
            exerciseType = "DURATION",
            durationSeconds = 10
        ),
        ExerciseResponseDto(
            id = "ex-4",
            name = "Dumbbell Curl",
            instructions = listOf(
                "Hold dumbbells at sides",
                "Curl weights toward shoulders",
                "Lower back to starting position"
            ),
            images = listOf("https://images.unsplash.com/photo-1581009146145-b5ef050c2e1e?w=400"),
            gymEquipments = listOf(GymEquipmentDto(id = 2, name = "Dumbbell")),
            focusArea = listOf("ARMS"),
            exerciseType = "REPS",
            reps = 12
        )
    )

    override suspend fun createExercise(exercise: ExerciseDto): ExerciseResponseDto {
        val newExercise = ExerciseResponseDto(
            id = "ex-new-${System.currentTimeMillis()}",
            name = exercise.name,
            instructions = exercise.instructions,
            images = exercise.images.ifEmpty { listOf("https://images.unsplash.com/photo-1571019614242-c5c5dee9f50b?w=400") },
            gymEquipments = exercise.gymEquipments.map { GymEquipmentDto(id = it, name = "") },
            focusArea = exercise.focusArea,
            exerciseType = exercise.exerciseType,
            reps = exercise.reps,
            durationSeconds = exercise.durationSeconds
        )
        mockExercises.add(0, newExercise)
        return newExercise
    }

    override suspend fun uploadExerciseImage(
        fileBytes: ByteArray,
        fileName: String,
        exerciseId: String
    ): String {
        val imageUrl = "https://images.unsplash.com/photo-1571019614242-c5c5dee9f50b?w=400"
        val index = mockExercises.indexOfFirst { it.id == exerciseId }
        if (index != -1) {
            val ex = mockExercises[index]
            mockExercises[index] = ex.copy(images = listOf(imageUrl))
        }
        return imageUrl
    }

    override suspend fun getExercisesByQuery(query: String): List<ExerciseResponseDto> {
        return mockExercises.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    override suspend fun getAllExercises(): List<ExerciseResponseDto> {
        return mockExercises.toList()
    }
}
