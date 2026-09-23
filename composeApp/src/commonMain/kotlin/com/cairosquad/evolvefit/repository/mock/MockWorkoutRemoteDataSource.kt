package com.cairosquad.evolvefit.repository.mock

import com.cairosquad.evolvefit.domain.model.FocusArea
import com.cairosquad.evolvefit.repository.equipment.remote.dto.GymEquipmentDto
import com.cairosquad.evolvefit.repository.exercise.remote.dto.ExerciseResponseDto
import com.cairosquad.evolvefit.repository.workout.remote.WorkoutRemoteDataSource
import com.cairosquad.evolvefit.repository.workout.remote.dto.CreateWorkoutRequest
import com.cairosquad.evolvefit.repository.workout.remote.dto.FavoritesWorkoutDto
import com.cairosquad.evolvefit.repository.workout.remote.dto.PlayedWorkoutDto
import com.cairosquad.evolvefit.repository.workout.remote.dto.WorkoutDetailsDto
import com.cairosquad.evolvefit.repository.workout.remote.dto.WorkoutDto
import com.cairosquad.evolvefit.repository.workout.remote.dto.WorkoutHistoryDto

class MockWorkoutRemoteDataSource : WorkoutRemoteDataSource {

    private val mockWorkouts = mutableListOf(
        WorkoutDto(
            id = "workout-1",
            name = "Full Body Strength",
            durationSeconds = 3600,
            imageUrl = "https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=400",
            focusArea = listOf(FocusArea.CHEST, FocusArea.BACK)
        ),
        WorkoutDto(
            id = "workout-2",
            name = "Core Crusher",
            durationSeconds = 1800,
            imageUrl = "https://images.unsplash.com/photo-1571019614242-c5c5dee9f50b?w=400",
            focusArea = listOf(FocusArea.CORE)
        ),
        WorkoutDto(
            id = "workout-3",
            name = "Leg Day Blast",
            durationSeconds = 3000,
            imageUrl = "https://images.unsplash.com/photo-1574680096145-d05b474e2155?w=400",
            focusArea = listOf(FocusArea.LEGS)
        ),
        WorkoutDto(
            id = "workout-4",
            name = "Upper Body Power",
            durationSeconds = 2400,
            imageUrl = "https://images.unsplash.com/photo-1581009146145-b5ef050c2e1e?w=400",
            focusArea = listOf(FocusArea.SHOULDERS, FocusArea.ARMS)
        ),
    )

    private val favoriteWorkouts = mutableListOf(
        FavoritesWorkoutDto(
            id = "workout-1",
            name = "Full Body Strength",
            durationSeconds = 3600,
            imageUrl = "https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=400",
            focusArea = listOf(FocusArea.CHEST, FocusArea.BACK)
        )
    )

    private val workoutHistory = mutableListOf(
        WorkoutHistoryDto(
            createdAt = "2026-08-31T10:00:00Z",
            durationSeconds = 2700L,
            exercisesCount = 8,
            imageUrl = "https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=400",
            level = "INTERMEDIATE",
            name = "Full Body Strength"
        ),
        WorkoutHistoryDto(
            createdAt = "2026-09-02T09:30:00Z",
            durationSeconds = 1800L,
            exercisesCount = 6,
            imageUrl = "https://images.unsplash.com/photo-1571019614242-c5c5dee9f50b?w=400",
            level = "BEGINNER",
            name = "Core Crusher"
        ),
    )

    private val workoutDetailsMap = mutableMapOf<String, WorkoutDetailsDto>()

    // Preset unique details for each workout ID
    private val presetWorkoutDetails = mapOf(
        "workout-1" to WorkoutDetailsDto(
            id = "workout-1",
            name = "Full Body Strength",
            durationSeconds = 3600,
            imageUrl = "https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=400",
            description = "A comprehensive full body workout targeting all major muscle groups.",
            level = "INTERMEDIATE",
            exercises = listOf(
                ExerciseResponseDto(
                    id = "ex-fb-1",
                    name = "Leg Press",
                    instructions = listOf("Sit on machine with feet shoulder-width on platform", "Lower weight until knees are at 90 degrees", "Push platform back up with legs (3 sets x 12 reps)"),
                    images = listOf("leg_press.jpeg"),
                    gymEquipments = listOf(GymEquipmentDto(id = 3, name = "Leg Press Machine")),
                    focusArea = listOf("LEGS", "GLUTES"),
                    exerciseType = "REPS",
                    reps = 12
                ),
                ExerciseResponseDto(
                    id = "ex-fb-2",
                    name = "Leg Curl",
                    instructions = listOf("Position legs under lever pad on machine", "Curl legs up toward glutes smoothly", "Lower legs back to start position (3 sets x 12 reps)"),
                    images = listOf("leg_curl.jpeg"),
                    gymEquipments = listOf(GymEquipmentDto(id = 4, name = "Leg Curl Machine")),
                    focusArea = listOf("LEGS"),
                    exerciseType = "REPS",
                    reps = 12
                ),
                ExerciseResponseDto(
                    id = "ex-fb-3",
                    name = "Lat Pulldown",
                    instructions = listOf("Grasp wide bar with overhand grip", "Pull bar down to upper chest engaging lats", "Return bar slowly to top position (3 sets x 12 reps)"),
                    images = listOf("lat_pulldown.jpeg"),
                    gymEquipments = listOf(GymEquipmentDto(id = 5, name = "Cable Machine")),
                    focusArea = listOf("BACK", "BICEPS"),
                    exerciseType = "REPS",
                    reps = 12
                ),
                ExerciseResponseDto(
                    id = "ex-fb-4",
                    name = "Seated Cable Row",
                    instructions = listOf("Sit at cable row station with feet on footrests", "Pull handles towards waist squeezing back", "Extend arms back smoothly (3 sets x 12 reps)"),
                    images = listOf("seated_cable_row.jpeg"),
                    gymEquipments = listOf(GymEquipmentDto(id = 5, name = "Cable Machine")),
                    focusArea = listOf("BACK"),
                    exerciseType = "REPS",
                    reps = 12
                ),
                ExerciseResponseDto(
                    id = "ex-fb-5",
                    name = "Chest Press",
                    instructions = listOf("Sit on machine with handles at chest height", "Push handles forward until arms are extended", "Return handles slowly to chest (3 sets x 12 reps)"),
                    images = listOf("chest_press.jpeg"),
                    gymEquipments = listOf(GymEquipmentDto(id = 6, name = "Chest Press Machine")),
                    focusArea = listOf("CHEST", "TRICEPS"),
                    exerciseType = "REPS",
                    reps = 12
                ),
                ExerciseResponseDto(
                    id = "ex-fb-6",
                    name = "Shoulder Press",
                    instructions = listOf("Sit on machine with handles at shoulder level", "Press handles upward overhead", "Lower back down to shoulder height (3 sets x 12 reps)"),
                    images = listOf("shoulder_press.jpeg"),
                    gymEquipments = listOf(GymEquipmentDto(id = 7, name = "Shoulder Press Machine")),
                    focusArea = listOf("SHOULDERS", "TRICEPS"),
                    exerciseType = "REPS",
                    reps = 12
                ),
                ExerciseResponseDto(
                    id = "ex-fb-7",
                    name = "Plank",
                    instructions = listOf("Place forearms on floor with elbows under shoulders", "Keep body in straight line from head to heels", "Hold position for 10 seconds"),
                    images = listOf("plank.jpeg"),
                    gymEquipments = emptyList(),
                    focusArea = listOf("CORE"),
                    exerciseType = "DURATION",
                    durationSeconds = 10
                ),
            )
        ),
        "workout-2" to WorkoutDetailsDto(
            id = "workout-2",
            name = "Core Crusher",
            durationSeconds = 1800,
            imageUrl = "https://images.unsplash.com/photo-1571019614242-c5c5dee9f50b?w=400",
            description = "Intense core routine designed to build abdominal strength and stability.",
            level = "BEGINNER",
            exercises = listOf(
                ExerciseResponseDto(
                    id = "ex-core-1",
                    name = "Plank Hold",
                    instructions = listOf("Forearms on floor", "Engage abs and glutes", "Hold steady line"),
                    images = listOf("https://images.unsplash.com/photo-1571019614242-c5c5dee9f50b?w=400"),
                    gymEquipments = emptyList(),
                    focusArea = listOf("CORE"),
                    exerciseType = "DURATION",
                    durationSeconds = 10
                ),
                ExerciseResponseDto(
                    id = "ex-core-2",
                    name = "Mountain Climbers",
                    instructions = listOf("Plank position", "Alternate knees to chest rapidly"),
                    images = listOf("https://images.unsplash.com/photo-1536922246289-88c42f957773?w=400"),
                    gymEquipments = emptyList(),
                    focusArea = listOf("CORE"),
                    exerciseType = "DURATION",
                    durationSeconds = 10
                ),
                ExerciseResponseDto(
                    id = "ex-core-3",
                    name = "Abdominal Crunches",
                    instructions = listOf("Lie on back knees bent", "Lift shoulders off floor engaging abs", "Lower down controlled"),
                    images = listOf("https://images.unsplash.com/photo-1571019614242-c5c5dee9f50b?w=400"),
                    gymEquipments = emptyList(),
                    focusArea = listOf("ABS"),
                    exerciseType = "REPS",
                    reps = 25
                ),
                ExerciseResponseDto(
                    id = "ex-core-4",
                    name = "Leg Raises",
                    instructions = listOf("Lie flat on back", "Raise legs to 90 degrees keeping them straight", "Lower slowly without touching floor"),
                    images = listOf("https://images.unsplash.com/photo-1517838277536-f5f99be501cd?w=400"),
                    gymEquipments = emptyList(),
                    focusArea = listOf("LOWER_ABS"),
                    exerciseType = "REPS",
                    reps = 20
                ),
                ExerciseResponseDto(
                    id = "ex-core-5",
                    name = "Russian Twists",
                    instructions = listOf("Sit leaning back slightly", "Rotate torso from side to side", "Touch hands to floor on each side"),
                    images = listOf("https://images.unsplash.com/photo-1541534741688-6078c6bfb5c5?w=400"),
                    gymEquipments = emptyList(),
                    focusArea = listOf("OBLIQUES"),
                    exerciseType = "REPS",
                    reps = 30
                ),
                ExerciseResponseDto(
                    id = "ex-core-6",
                    name = "Bicycle Crunches",
                    instructions = listOf("Lie back hands behind head", "Bring right elbow to left knee while extending right leg", "Alternate sides"),
                    images = listOf("https://images.unsplash.com/photo-1571019614242-c5c5dee9f50b?w=400"),
                    gymEquipments = emptyList(),
                    focusArea = listOf("CORE", "OBLIQUES"),
                    exerciseType = "REPS",
                    reps = 20
                )
            )
        ),
        "workout-3" to WorkoutDetailsDto(
            id = "workout-3",
            name = "Leg Day Blast",
            durationSeconds = 3000,
            imageUrl = "https://images.unsplash.com/photo-1574680096145-d05b474e2155?w=400",
            description = "High-energy lower body workout for building quads, hamstrings, and glutes.",
            level = "ADVANCED",
            exercises = listOf(
                ExerciseResponseDto(
                    id = "ex-leg-1",
                    name = "Bodyweight Squat",
                    instructions = listOf("Feet shoulder-width apart", "Squat down deeply", "Drive through heels to stand"),
                    images = listOf("https://images.unsplash.com/photo-1574680096145-d05b474e2155?w=400"),
                    gymEquipments = emptyList(),
                    focusArea = listOf("QUADS", "GLUTES"),
                    exerciseType = "REPS",
                    reps = 25
                ),
                ExerciseResponseDto(
                    id = "ex-leg-2",
                    name = "Walking Lunges",
                    instructions = listOf("Step forward with one leg", "Lower back knee toward floor", "Push forward into next step"),
                    images = listOf("https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=400"),
                    gymEquipments = emptyList(),
                    focusArea = listOf("QUADS", "HAMSTRINGS"),
                    exerciseType = "REPS",
                    reps = 20
                ),
                ExerciseResponseDto(
                    id = "ex-leg-3",
                    name = "Calf Raises",
                    instructions = listOf("Stand tall", "Rise up onto toes", "Hold for 1 second", "Lower heels down"),
                    images = listOf("https://images.unsplash.com/photo-1574680096145-d05b474e2155?w=400"),
                    gymEquipments = emptyList(),
                    focusArea = listOf("CALVES"),
                    exerciseType = "REPS",
                    reps = 30
                ),
                ExerciseResponseDto(
                    id = "ex-leg-4",
                    name = "Wall Sit",
                    instructions = listOf("Back flat against wall", "Lower into 90-degree thigh angle", "Hold position"),
                    images = listOf("https://images.unsplash.com/photo-1517838277536-f5f99be501cd?w=400"),
                    gymEquipments = emptyList(),
                    focusArea = listOf("QUADS"),
                    exerciseType = "DURATION",
                    durationSeconds = 45
                ),
                ExerciseResponseDto(
                    id = "ex-leg-5",
                    name = "Sumo Squat",
                    instructions = listOf("Wide stance toes pointed out", "Lower hips low", "Squeeze glutes at top"),
                    images = listOf("https://images.unsplash.com/photo-1574680096145-d05b474e2155?w=400"),
                    gymEquipments = emptyList(),
                    focusArea = listOf("INNER_THIGHS", "GLUTES"),
                    exerciseType = "REPS",
                    reps = 20
                ),
                ExerciseResponseDto(
                    id = "ex-leg-6",
                    name = "Glute Bridges",
                    instructions = listOf("Lie back knees bent feet flat", "Drive hips up squeezing glutes", "Lower down slowly"),
                    images = listOf("https://images.unsplash.com/photo-1571019614242-c5c5dee9f50b?w=400"),
                    gymEquipments = emptyList(),
                    focusArea = listOf("GLUTES", "HAMSTRINGS"),
                    exerciseType = "REPS",
                    reps = 25
                )
            )
        ),
        "workout-4" to WorkoutDetailsDto(
            id = "workout-4",
            name = "Upper Body Power",
            durationSeconds = 2400,
            imageUrl = "https://images.unsplash.com/photo-1581009146145-b5ef050c2e1e?w=400",
            description = "Upper body strength routine focusing on chest, back, shoulders, and arms.",
            level = "INTERMEDIATE",
            exercises = listOf(
                ExerciseResponseDto(
                    id = "ex-up-1",
                    name = "Push-up",
                    instructions = listOf("Hands shoulder-width apart", "Lower chest", "Push up strong"),
                    images = listOf("https://images.unsplash.com/photo-1598971639058-fab3c3109a00?w=400"),
                    gymEquipments = emptyList(),
                    focusArea = listOf("CHEST", "TRICEPS"),
                    exerciseType = "REPS",
                    reps = 25
                ),
                ExerciseResponseDto(
                    id = "ex-up-2",
                    name = "Dumbbell Row",
                    instructions = listOf("Flat back hinge", "Pull dumbbell to hip", "Squeeze back"),
                    images = listOf("https://images.unsplash.com/photo-1581009146145-b5ef050c2e1e?w=400"),
                    gymEquipments = listOf(GymEquipmentDto(id = 2, name = "Dumbbell")),
                    focusArea = listOf("BACK"),
                    exerciseType = "REPS",
                    reps = 15
                ),
                ExerciseResponseDto(
                    id = "ex-up-3",
                    name = "Dumbbell Shoulder Press",
                    instructions = listOf("Dumbbells at shoulders", "Press straight overhead", "Lower smoothly"),
                    images = listOf("https://images.unsplash.com/photo-1541534741688-6078c6bfb5c5?w=400"),
                    gymEquipments = listOf(GymEquipmentDto(id = 2, name = "Dumbbell")),
                    focusArea = listOf("SHOULDERS"),
                    exerciseType = "REPS",
                    reps = 15
                ),
                ExerciseResponseDto(
                    id = "ex-up-4",
                    name = "Dumbbell Bicep Curl",
                    instructions = listOf("Stand tall dumbbells at sides", "Curl to shoulders", "Lower slowly"),
                    images = listOf("https://images.unsplash.com/photo-1581009146145-b5ef050c2e1e?w=400"),
                    gymEquipments = listOf(GymEquipmentDto(id = 2, name = "Dumbbell")),
                    focusArea = listOf("BICEPS"),
                    exerciseType = "REPS",
                    reps = 15
                ),
                ExerciseResponseDto(
                    id = "ex-up-5",
                    name = "Tricep Dips",
                    instructions = listOf("Hands on bench/chair behind you", "Lower hips bending elbows", "Push back up"),
                    images = listOf("https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=400"),
                    gymEquipments = emptyList(),
                    focusArea = listOf("TRICEPS"),
                    exerciseType = "REPS",
                    reps = 20
                )
            )
        )
    )

    override suspend fun getFavoriteWorkout(): List<FavoritesWorkoutDto> {
        return favoriteWorkouts.toList()
    }

    override suspend fun createWorkout(request: CreateWorkoutRequest): WorkoutDetailsDto {
        val newId = "new-workout-${System.currentTimeMillis()}"
        val created = WorkoutDetailsDto(
            id = newId,
            name = request.name,
            durationSeconds = 1800,
            imageUrl = "https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=400",
            description = request.description,
            level = request.level
        )
        workoutDetailsMap[newId] = created
        mockWorkouts.add(
            WorkoutDto(
                id = newId,
                name = request.name,
                durationSeconds = 1800,
                imageUrl = "https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=400",
                focusArea = listOf(FocusArea.CHEST, FocusArea.BACK)
            )
        )
        return created
    }

    override suspend fun getWorkoutDetails(workoutId: String): WorkoutDetailsDto {
        return workoutDetailsMap[workoutId]
            ?: presetWorkoutDetails[workoutId]
            ?: presetWorkoutDetails.values.first()
    }

    override suspend fun getSuggestedWorkouts(): List<WorkoutDto> {
        return mockWorkouts.toList()
    }

    override suspend fun getCommunityWorkouts(): List<WorkoutDto> {
        return mockWorkouts.toList()
    }

    override suspend fun getWorkoutsByFocusArea(focusArea: FocusArea): List<WorkoutDto> {
        return mockWorkouts.filter { it.focusArea.contains(focusArea) }
            .ifEmpty { mockWorkouts.toList() }
    }

    override suspend fun submitPlayedWorkout(playedWorkout: PlayedWorkoutDto) {
        val targetWorkout = mockWorkouts.find { it.id == playedWorkout.workoutId }
        val targetDetails = getWorkoutDetails(playedWorkout.workoutId)
        val levelUpper = targetDetails.level.uppercase()
        val validLevel = if (levelUpper in listOf("BEGINNER", "INTERMEDIATE", "ADVANCED")) levelUpper else "INTERMEDIATE"
        workoutHistory.add(
            0,
            WorkoutHistoryDto(
                createdAt = kotlinx.datetime.Clock.System.now().toString(),
                durationSeconds = playedWorkout.durationSeconds.toLong(),
                exercisesCount = targetDetails.exercises.size.ifZero { 6 },
                imageUrl = targetWorkout?.imageUrl ?: "https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=400",
                level = validLevel,
                name = targetWorkout?.name ?: "Completed Workout"
            )
        )
    }

    private fun Int.ifZero(default: () -> Int): Int = if (this == 0) default() else this

    override suspend fun getCommunityWorkoutsByFocusArea(focusArea: FocusArea): List<WorkoutDto> {
        return mockWorkouts.filter { it.focusArea.contains(focusArea) }
            .ifEmpty { mockWorkouts.toList() }
    }

    override suspend fun getWorkoutHistory(): List<WorkoutHistoryDto> {
        return workoutHistory.toList()
    }

    override suspend fun addFavoriteWorkout(workOutId: String) {
        val target = mockWorkouts.find { it.id == workOutId }
        if (target != null && favoriteWorkouts.none { it.id == workOutId }) {
            favoriteWorkouts.add(
                FavoritesWorkoutDto(
                    id = target.id,
                    name = target.name,
                    durationSeconds = target.durationSeconds ?: 1800,
                    imageUrl = target.imageUrl,
                    focusArea = target.focusArea
                )
            )
        }
    }

    override suspend fun deleteFavoriteWorkout(workoutId: String) {
        favoriteWorkouts.removeAll { it.id == workoutId }
    }

    override suspend fun uploadWorkoutImage(
        fileBytes: ByteArray,
        fileName: String,
        exerciseId: String
    ): String {
        return "https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=400"
    }
}
