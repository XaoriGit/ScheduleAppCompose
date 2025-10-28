package ru.xaori.schedule.presentation.screen.clientChoice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import ru.xaori.schedule.R
import ru.xaori.schedule.core.ApiError
import ru.xaori.schedule.core.UIState
import ru.xaori.schedule.presentation.viewmodel.ClientChoiceViewModel
import ru.xaori.schedule.domain.model.ClientTypeDestination
import ru.xaori.schedule.presentation.screen.clientChoice.component.ButtonClientChoice
import ru.xaori.schedule.presentation.common.AnimatedAppBar
import ru.xaori.schedule.presentation.state.AnimatedAppBarStatus.*
import ru.xaori.schedule.presentation.viewmodel.SnackbarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientChoiceScreen(
    showCancelButton: Boolean,
    goToBack: () -> Unit,
    goToMain: () -> Unit,
    viewModel: ClientChoiceViewModel = koinViewModel(),
    snackbarViewModel: SnackbarViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedTabIndex by viewModel.selectedTabIndex.collectAsState()
    var isNavigate by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(0.dp, 8.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        AnimatedAppBar(
            "Расписание",
            when (val state = uiState) {
                is UIState.Loading -> Loading
                is UIState.Success -> SubTitle(
                    "Выбрать расписание"
                )

                is UIState.Error -> when (val errorState = state.error) {
                    is ApiError.Network -> SubTitleError("Нет интернета")
                    is ApiError.Server -> SubTitleError("Ошибка сервера")
                    is ApiError.Unknown -> SubTitleError("Неизвестная ошибка ${errorState.error.message}")
                }
            },
        ) {
            if (showCancelButton) {
                IconButton(
                    onClick = {
                        if (!isNavigate) {
                            goToBack()
                            isNavigate = true
                        }
                    },
                    modifier = Modifier.size(28.dp),
                ) {
                    Icon(
                        painterResource(R.drawable.ic_cancel),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
        SecondaryTabRow(
            selectedTabIndex = selectedTabIndex,
            divider = {},
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(12.dp))

        ) {
            ClientTypeDestination.entries.forEachIndexed { index, type ->
                Tab(selected = selectedTabIndex == index, onClick = {
                    viewModel.onTabSelected(ClientTypeDestination.entries[index].ordinal)
                }, text = {
                    Text(
                        type.title,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                })
            }
        }
        TextField(
            value = searchQuery,
            onValueChange = { value -> viewModel.onSearchQueryChange(value) },
            label = {
                Text(
                    "Введите группу или фамилию",
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Unspecified,
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Unspecified,
                imeAction = ImeAction.Unspecified
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .focusRequester(focusRequester),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedLabelColor = MaterialTheme.colorScheme.primary
            )
        )
        when (val state = uiState) {
            is UIState.Loading -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(36.dp),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surface,
                    )
                }
            }

            is UIState.Success -> {
                val query = searchQuery.trim()
                LazyColumn {
                    if (query.isEmpty()) {
                        if (selectedTabIndex == ClientTypeDestination.Group.ordinal) {
                            itemsIndexed(state.data.groups) { _, group ->
                                ButtonClientChoice(group) {
                                    viewModel.onClickClientChoice(it, goToMain)
                                }
                            }
                        } else {
                            itemsIndexed(state.data.groups) { _, teacher ->
                                ButtonClientChoice(teacher) {
                                    viewModel.onClickClientChoice(it, goToMain)
                                }
                            }
                        }
                    } else {
                        val groups = state.data.groups.filter {
                            it.contains(query, ignoreCase = true)
                        }
                        val teachers = state.data.teachers.filter {
                            it.contains(query, ignoreCase = true)
                        }

                        val results = groups + teachers

                        if (results.isNotEmpty()) {
                            itemsIndexed(results) { _, item ->
                                ButtonClientChoice(item) {
                                    viewModel.onClickClientChoice(it, goToMain)
                                }
                            }
                        } else {
                            item {
                                Text(
                                    "Ничего не найдено",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }


            is UIState.Error -> {
                Button(
                    onClick = { viewModel.getClients() },
                    contentPadding = PaddingValues(16.dp, 12.dp),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
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