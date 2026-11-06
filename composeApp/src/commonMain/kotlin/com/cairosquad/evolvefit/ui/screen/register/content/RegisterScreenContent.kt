package com.cairosquad.evolvefit.ui.screen.register.content

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.cairosquad.evolvefit.design_system.component.PrimaryButton
import com.cairosquad.evolvefit.design_system.component.appbar.IndicatorBar
import com.cairosquad.evolvefit.design_system.theme.AppMotion
import com.cairosquad.evolvefit.design_system.theme.Theme
import com.cairosquad.evolvefit.viewmodel.register.RegisterInteractionListener
import com.cairosquad.evolvefit.viewmodel.register.RegisterScreenState
import com.cairosquad.evolvefit.viewmodel.register.RegisterViewModel
import evolvefit.composeapp.generated.resources.Res
import evolvefit.composeapp.generated.resources.next
import evolvefit.composeapp.generated.resources.start_now
import kotlin.math.absoluteValue
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreenContent(
    state: RegisterScreenState,
    listener: RegisterInteractionListener,
) {
    val focusManager = LocalFocusManager.current

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { RegisterViewModel.MAX_STEPS }
    )

    SyncPageWithScreenState(
        state = state,
        pagerState = pagerState
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Theme.color.surfaces.surface)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding(),
    ) {
        IndicatorBar(
            modifier = Modifier.padding(horizontal = 16.dp),
            currentStep = state.currentStep,
            totalSteps = RegisterViewModel.MAX_STEPS,
            onClickLeadingIcon = listener::onBackClicked,
        )
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = pagerState,
            userScrollEnabled = false
        ) { pageIndex ->
            val pageOffset = ((pagerState.currentPage - pageIndex) + pagerState.currentPageOffsetFraction).absoluteValue
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        // Model A: Shared Axis (Slide & Fade dengan Emphasized Spring)
                        val offsetFraction = pageOffset.coerceIn(0f, 1f)
                        val pageScale = 1f - (0.04f * offsetFraction)
                        scaleX = pageScale
                        scaleY = pageScale
                        alpha = 1f - (0.5f * offsetFraction)
                    }
            ) {
                when (pageIndex) {
                    0 -> RegisterScreenContentSelectGender(state, listener)
                    1 -> RegisterScreenContentSelectUnitsOfMeasurement(state, listener)
                    2 -> RegisterScreenContentSelectHeightAndWeight(state, listener)
                    3 -> RegisterScreenContentChooseYourGoal(state, listener)
                    4 -> RegisterScreenContentSelectYourTools(state, listener)
                    5 -> RegisterScreenContentSelectWorkoutDays(state, listener)
                    6 -> RegisterScreenContentUserNamePasswordDateOfBirth(state, listener)
                }
            }
        }
        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            text =
                if (state.currentStep == 7)
                    stringResource(Res.string.start_now)
                else
                    stringResource(Res.string.next),
            onClick = {
                focusManager.clearFocus()
                if (state.currentStep == 7) {
                    listener.onStartNowClicked()
                } else {
                    listener.onNextClicked()
                }
            },
            isEnabled = state.isNextButtonEnabled,
            isLoading = state.isLoading
        )

    }
}

@Composable
private fun SyncPageWithScreenState(
    state: RegisterScreenState,
    pagerState: PagerState
) {
    val currentStepIndex = state.currentStep - 1
    LaunchedEffect(currentStepIndex) {
        if (currentStepIndex == pagerState.currentPage) return@LaunchedEffect
        try {
            pagerState.animateScrollToPage(
                page = currentStepIndex,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = AppMotion.EmphasizedEasing
                )
            )
        } catch (_: Exception) {  }
    }
}