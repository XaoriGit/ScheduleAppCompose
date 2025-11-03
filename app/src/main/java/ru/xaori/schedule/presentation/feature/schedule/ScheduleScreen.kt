package ru.xaori.schedule.presentation.feature.schedule

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import ru.xaori.schedule.R
import ru.xaori.schedule.core.ApiError
import ru.xaori.schedule.core.UIState
import ru.xaori.schedule.domain.model.ScheduleUiData
import ru.xaori.schedule.presentation.common.AnimatedAppBar
import ru.xaori.schedule.presentation.feature.clientChoice.ClientChoiceSheetContent
import ru.xaori.schedule.presentation.feature.schedule.components.ScheduleList
import ru.xaori.schedule.presentation.feature.schedule.components.ScheduleSkeleton
import ru.xaori.schedule.presentation.feature.schedule.components.WeekDaysRow
import ru.xaori.schedule.presentation.state.AnimatedAppBarStatus.Loading
import ru.xaori.schedule.presentation.state.AnimatedAppBarStatus.SubTitle
import ru.xaori.schedule.presentation.state.AnimatedAppBarStatus.SubTitleError
import ru.xaori.schedule.presentation.state.SnackbarType
import ru.xaori.schedule.presentation.viewmodel.ScheduleViewModel
import ru.xaori.schedule.presentation.viewmodel.SnackbarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    goToSettings: () -> Unit,
    viewModel: ScheduleViewModel = koinViewModel(),
    snackbarViewModel: SnackbarViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var isRefreshing by remember { mutableStateOf(false) }
    val stateRefresh = rememberPullToRefreshState()

    var showClientChoiceSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val pagerState = rememberPagerState(
        initialPage = 0, pageCount = {
            val state = uiState
            if (state is UIState.Success) state.data.scheduleData.schedules.size
            else 0
        })

    LaunchedEffect(uiState) {
        if (uiState !is UIState.Loading && isRefreshing) {
            isRefreshing = false
        }
    }

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is UIState.Error -> {
                when (val error = state.error) {
                    is ApiError.Network -> {
                        snackbarViewModel.showSnackbar(
                            message = "Нет интернета", type = SnackbarType.ERROR
                        )
                    }

                    is ApiError.Server -> {
                        snackbarViewModel.showSnackbar(
                            message = "Ошибка сервера: ${error.code}", type = SnackbarType.ERROR
                        )
                    }

                    else -> {}
                }
            }

            else -> {}
        }
    }

    LaunchedEffect(uiState) {
        if (uiState is UIState.Success && (uiState as UIState.Success<ScheduleUiData>).data.isCached) {
            snackbarViewModel.showSnackbar(
                message = "Расписание может быть не актуальным", type = SnackbarType.ERROR
            )
        }
    }

    LaunchedEffect(Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }
            val token = task.result
            Log.d("FCM", "Token: $token")
        }
    }


    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(0.dp, 8.dp),
    ) {
        AnimatedAppBar(
            "Расписание", when (val state = uiState) {
                is UIState.Loading -> Loading
                is UIState.Success -> SubTitle(
                    state.data.scheduleData.clientName
                )

                is UIState.Error -> when (val errorState = state.error) {
                    is ApiError.Network -> SubTitleError("Нет интернета")
                    is ApiError.Server -> SubTitleError("Ошибка сервера")
                    is ApiError.Unknown -> SubTitleError("Неизвестная ошибка ${errorState.error.message}")
                }
            }, goToSettings = {
                showClientChoiceSheet = true
            }) {
            IconButton(
                onClick = { showClientChoiceSheet = true },
                modifier = Modifier.size(28.dp),
            ) {
                Icon(
                    painterResource(R.drawable.ic_settings),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        AnimatedContent(
            targetState = uiState
        ) {
            when (val state = it) {
                is UIState.Loading -> ScheduleSkeleton()

                is UIState.Success -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        WeekDaysRow(
                            pagerState.currentPage, state.data.scheduleData.schedules
                        ) { value ->
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(value, animationSpec = tween())
                            }
                        }
                        PullToRefreshBox(
                            state = stateRefresh,
                            isRefreshing = isRefreshing,
                            onRefresh = {
                                isRefreshing = true
                                viewModel.getSchedule()
                            },
                            indicator = {
                                Indicator(
                                    modifier = Modifier.align(Alignment.TopCenter),
                                    isRefreshing = isRefreshing,
                                    containerColor = MaterialTheme.colorScheme.surface,
                                    color = MaterialTheme.colorScheme.primary,
                                    state = stateRefresh
                                )
                            },
                        ) {
                            ScheduleList(
                                state.data.scheduleData.schedules,
                                state.data.scheduleData.lastUpdate,
                                pagerState
                            )
                        }
                    }
                }

                is UIState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            20.dp, Alignment.CenterVertically
                        )
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Что-то пошло не так",
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                "Проверьте интернет и попробуйте снова",
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Button(
                            onClick = { viewModel.getSchedule() },
                            contentPadding = PaddingValues(16.dp, 12.dp),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Icon(
                                painterResource(R.drawable.ic_refresh),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondary,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(20.dp),

                                )
                            Text(
                                "Попробовать снова",
                                color = MaterialTheme.colorScheme.onSecondary,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
            }
        }
    }

    if (showClientChoiceSheet) {
        ModalBottomSheet(
            onDismissRequest = { showClientChoiceSheet = false },
            sheetState = sheetState,
        ) {
            ClientChoiceSheetContent(
                onClientChosen = {
                    showClientChoiceSheet = false
                    viewModel.getSchedule()
                }
            )
        }
    }

}