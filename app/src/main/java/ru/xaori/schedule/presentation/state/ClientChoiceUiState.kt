package ru.xaori.schedule.presentation.state

import ru.xaori.schedule.domain.model.ClientChoiceResponse
import ru.xaori.schedule.domain.model.ClientTypeDestination


data class ClientChoiceUiState(
    val searchQuery: String = "",
    val selectedTabIndex: Int = 0,
    val dataState: ClientChoiceDataState = ClientChoiceDataState.Loading
)

sealed class ClientChoiceDataState {
    object Loading: ClientChoiceDataState()
    data class Success(
        val clientChoice: ClientChoiceResponse,
        val searchQuery: String = "",
        val selectedTab: Int = ClientTypeDestination.Group.ordinal
    ): ClientChoiceDataState()
    data class Error(val detail: String): ClientChoiceDataState()
}