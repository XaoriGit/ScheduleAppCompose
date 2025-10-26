package ru.xaori.schedule.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.xaori.schedule.core.ApiError
import ru.xaori.schedule.core.UIState
import ru.xaori.schedule.domain.model.ClientChoiceResponse
import ru.xaori.schedule.domain.model.ClientTypeDestination
import ru.xaori.schedule.domain.repository.ClientChoiceRepository

class ClientChoiceViewModel(private val clientChoiceRepository: ClientChoiceRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow<UIState<ClientChoiceResponse>>(UIState.Loading)
    val uiState: StateFlow<UIState<ClientChoiceResponse>> = _uiState

    private val _searchQuery = MutableStateFlow<String>("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedTabIndex = MutableStateFlow<Int>(ClientTypeDestination.Group.ordinal)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex

    init {
        getClients()
    }

    fun getClients() {
        _uiState.value = UIState.Loading

        viewModelScope.launch {

            clientChoiceRepository.getClients().fold(
                onSuccess = { data -> _uiState.value = UIState.Success(data) },
                onFailure = { throwable ->
                    val apiError = throwable as? ApiError ?: ApiError.Unknown(throwable)
                    _uiState.value = UIState.Error(apiError)
                },

                )
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun onTabSelected(index: Int) {
        _selectedTabIndex.value = index
    }

    fun onClickClientChoice(value: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            clientChoiceRepository.setClient(value)
            onComplete()
        }
    }
}