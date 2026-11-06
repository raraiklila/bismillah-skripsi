package com.cairosquad.evolvefit.viewmodel.create_workout

import androidx.lifecycle.viewModelScope
import com.cairosquad.evolvefit.domain.entity.Exercise
import com.cairosquad.evolvefit.domain.entity.Workout
import com.cairosquad.evolvefit.domain.usecase.exercise.ManageExerciseUseCase
import com.cairosquad.evolvefit.domain.usecase.profile.ManagePreferencesUseCase
import com.cairosquad.evolvefit.domain.usecase.workout.ManageWorkoutUseCase
import com.cairosquad.evolvefit.viewmodel.base.BaseViewModel
import com.cairosquad.evolvefit.viewmodel.create_workout.CreateWorkOutEffect.NavigateBack
import com.cairosquad.evolvefit.viewmodel.create_workout.CreateWorkOutScreenState.WorkoutLevel
import com.cairosquad.evolvefit.viewmodel.more.MoreScreenState.Theme
import com.cairosquad.evolvefit.viewmodel.onboarding.models.UiImage
import com.cairosquad.evolvefit.viewmodel.utils.asByteArray
import evolvefit.composeapp.generated.resources.Res
import evolvefit.composeapp.generated.resources.create
import evolvefit.composeapp.generated.resources.create_dark
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CreateWorkoutViewModel(
    private val manageWorkoutUseCase: ManageWorkoutUseCase,
    private val manageExerciseUseCase: ManageExerciseUseCase,
    private val managePreferencesUseCase: ManagePreferencesUseCase
) : BaseViewModel<CreateWorkOutScreenState, CreateWorkOutEffect>(CreateWorkOutScreenState()),
    CreateWorkOutInteractionListener {

    init {
        loadData()
    }

    private fun loadData() {
        loadExercises()
        updateCreateWorkoutImage()
    }

    private fun updateCreateWorkoutImage() {
        val theme = managePreferencesUseCase.getTheme()
        var image = UiImage.ImageResource(Res.drawable.create)
        if (theme == Theme.DARK) {
            image = UiImage.ImageResource(Res.drawable.create_dark)
        }
        updateState { it.copy(image = image) }
    }

    private fun pushWorkoutImage(id: String) {
        val image = screenState.value.image
        tryToCall(
            block = {
                val imageFileData = image.asByteArray()
                manageWorkoutUseCase.uploadWorkoutImage(
                    imageFileData.bytes,
                    imageFileData.fileName,
                    id
                )
            },
            onSuccess = { imageUrl ->
                updateState { it.copy(image = UiImage.ImageUrl(imageUrl)) }
            },
            onError = {}
        )
    }

    private fun handleExercisesResultSuccess(exercises: List<Exercise>) {
        val oldExercises = screenState.value.allExercises
        val currentNewlyAdded = screenState.value.newlyAddExercises
        val fetchedExercises = exercises.map { it.toUiState() }

        // Cari exercise baru yang belum ada di oldExercises maupun newlyAddExercises sebelumnya
        val brandNewExercises = if (oldExercises.isNotEmpty() || currentNewlyAdded.isNotEmpty()) {
            val knownIds = (oldExercises + currentNewlyAdded).map { it.id }.toSet()
            fetchedExercises.filterNot { it.id in knownIds }
        } else {
            emptyList()
        }

        val updatedNewlyAdded = (currentNewlyAdded + brandNewExercises).distinctBy { it.id }
        val updatedNewlyAddedIds = updatedNewlyAdded.map { it.id }.toSet()
        val updatedSelected = (screenState.value.selectedExercises + brandNewExercises).distinctBy { it.id }

        // Sisa exercises untuk bagian "All Exercises" (tanpa menduplikasi newlyAddExercises)
        val remainingExercises = fetchedExercises.filterNot { it.id in updatedNewlyAddedIds }

        val query = screenState.value.searchQuery
        val filtered = if (query.isBlank()) {
            remainingExercises
        } else {
            remainingExercises.filter { it.name.contains(query, ignoreCase = true) }
        }

        updateState {
            it.copy(
                status = CreateWorkOutScreenState.ScreenStatus.SUCCESS,
                newlyAddExercises = updatedNewlyAdded,
                selectedExercises = updatedSelected,
                allExercises = remainingExercises,
                filteredExercises = filtered,
                exerciseCount = updatedSelected.size,
                isAddExercisesEnabled = updatedSelected.isNotEmpty()
            )
        }
    }

    fun loadExercises() {
        tryToCall(
            onStart = ::handleWorkoutLoading,
            block = { manageExerciseUseCase.getAllExercises() },
            onSuccess = ::handleExercisesResultSuccess,
            onError = ::handleWorkoutError,
            onEnd = {}
        )
    }

    override fun onImageSelected(image: UiImage) {
        updateState {
            it.copy(
                image = image,
                isImagePickerOpen = false,
                isNextEnabled = validate(it.name, it.level?.name ?: "", it.description, image)
            )
        }
    }

    override fun onImageClicked() {
        updateState { it.copy(isImagePickerOpen = true) }
    }

    override fun onImagePickerDismiss() {
        updateState { it.copy(isImagePickerOpen = false) }
    }

    override fun onRetryClicked() {
        loadData()
    }

    override fun onRefresh() {
        updateState {
            it.copy(isRefreshing = true)
        }
        loadData()
        viewModelScope.launch {
            delay(500L)
            updateState { it.copy(isRefreshing = false) }
        }
    }

    override fun onNameChanged(newName: String) {
        val cleanName = if (newName.isBlank()) "" else newName
        updateState {
            it.copy(
                name = cleanName,
                isNextEnabled = validate(cleanName, it.level?.name ?: "", it.description, it.image)
            )
        }
    }

    override fun onGoalSelected(goal: WorkoutLevel) {
        updateState {
            it.copy(
                level = goal,
                isGoalExpanded = false,
                isNextEnabled = validate(it.name, goal.name, it.description, it.image)
            )
        }
    }

    override fun onGoalIconClicked() {
        updateState { it.copy(isGoalExpanded = !it.isGoalExpanded) }
    }

    override fun onDescriptionChanged(desc: String) {
        val trimmed = desc.take(3000)
        updateState {
            it.copy(
                description = trimmed,
                isNextEnabled = validate(it.name, it.level?.name ?: "", trimmed, it.image)
            )
        }
    }

    private fun validate(name: String, goal: String, desc: String, image: UiImage): Boolean {
        return name.isNotBlank() && goal.isNotBlank() && desc.isNotBlank() && image is UiImage.ImageFile
    }

    override fun onExerciseCheckedChanged(exercise: CreateWorkOutScreenState.ExerciseUiState) {
        updateState { currentState ->
            val selected = currentState.selectedExercises.toMutableList().apply {
                if (any { it.id == exercise.id }) removeAll { it.id == exercise.id }
                else add(exercise)
            }
            currentState.copy(
                selectedExercises = selected,
                exerciseCount = selected.size,
                isAddExercisesEnabled = selected.isNotEmpty()
            )
        }
    }

    override fun onBackClicked() {
        updateState { it.copy(currentStep = CreateWorkOutScreenState.CreateWorkoutStep.DETAILS) }
    }

    override fun onExitClicked() {
        updateState { it.copy(showExitBottomSheet = true) }
    }

    override fun onExitConfirmClicked() {
        updateState { it.copy(showExitBottomSheet = false) }
        sendEffect(NavigateBack)
    }

    override fun onCancelExitClicked() {
        updateState { it.copy(showExitBottomSheet = false) }
    }

    override fun onAddClicked() {
        sendEffect(CreateWorkOutEffect.NavigateToCreateExercise)
    }

    override fun onSearchQueryChanged(query: String) {
        updateState { state ->
            val filtered =
                if (query.isBlank()) state.allExercises
                else state.allExercises.filter { it.name.contains(query, ignoreCase = true) }
            state.copy(
                searchQuery = query,
                filteredExercises = filtered
            )
        }
    }

    override fun onNextClicked() {
        updateState { it.copy(currentStep = CreateWorkOutScreenState.CreateWorkoutStep.EXERCISES) }
    }

    private fun handleWorkoutSuccess(createWorkout: Workout) {
        updateState { it.copy(status = CreateWorkOutScreenState.ScreenStatus.SUCCESS) }
        updateState { it.copy(isLoading = false) }
        pushWorkoutImage(createWorkout.id)
        sendEffect(CreateWorkOutEffect.NavigateToWorkouts)
    }

    private fun handleWorkoutError(throwable: Throwable) {
        throwable.printStackTrace()
        updateState { it.copy(status = CreateWorkOutScreenState.ScreenStatus.ERROR) }
        updateState { it.copy(isLoading = false) }
    }

    private fun handleWorkoutLoading() {
        updateState { it.copy(status = CreateWorkOutScreenState.ScreenStatus.LOADING) }
    }

    override fun onAddWorkoutClicked() {
        val selectedExercises = screenState.value.selectedExercises
        val workout = screenState.value.toDomainWorkout(selectedExercises)

        tryToCall(
            onStart = { updateState { it.copy(isLoading = true) } },
            block = { manageWorkoutUseCase.createWorkOut(workout) },
            onSuccess = ::handleWorkoutSuccess,
            onError = ::handleWorkoutError,
        )
    }
}