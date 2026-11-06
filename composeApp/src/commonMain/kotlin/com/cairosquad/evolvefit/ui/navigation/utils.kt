package com.cairosquad.evolvefit.ui.navigation

import androidx.navigation.NavHostController

fun <T> NavHostController.getFromSavedState(key: String = DEFAULT_KRY): T? {
    return currentBackStackEntry?.savedStateHandle?.get(key)
}

fun <T> NavHostController.saveInSavedState(value: T, key: String = DEFAULT_KRY) {
    currentBackStackEntry?.savedStateHandle?.set(key, value)
}

fun NavHostController.clearSavedState(key: String = DEFAULT_KRY) {
    currentBackStackEntry?.savedStateHandle?.remove<Any>(key)
}

fun (() -> Unit).clearSavedStateAfterInvoke(navController: NavHostController, key: String = DEFAULT_KRY): (() -> Unit) {
    return {
        invoke()
        navController.clearSavedState(key)
    }
}

private const val DEFAULT_KRY = "DEFAULT_KRY"