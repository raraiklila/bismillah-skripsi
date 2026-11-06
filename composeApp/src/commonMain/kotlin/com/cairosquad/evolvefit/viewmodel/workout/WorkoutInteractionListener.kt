package com.cairosquad.evolvefit.viewmodel.workout

interface WorkoutInteractionListener {
    fun onFocusAreaSelected(focusArea: WorkoutScreenState.FocusAreaUiState)
    fun onSearchQueryChanged(query: String)
    fun onAddWorkoutClicked()
    fun onCommunityClicked()
    fun onRefresh()
    fun onRetryClicked()
    fun onWorkoutClicked(id: String)
}