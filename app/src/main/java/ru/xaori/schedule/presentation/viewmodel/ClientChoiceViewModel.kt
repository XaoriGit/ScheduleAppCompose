package ru.xaori.schedule.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.xaori.schedule.data.repository.ClientChoiceRepository
import ru.xaori.schedule.presentation.state.ClientChoiceDataState
import ru.xaori.schedule.presentation.state.ClientChoiceUiState
import ru.xaori.schedule.presentation.state.AppError
import ru.xaori.schedule.presentation.state.ResultWrapper

class ClientChoiceViewModel(private val clientChoiceRepository: ClientChoiceRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(ClientChoiceUiState())
    val uiState: StateFlow<ClientChoiceUiState> = _uiState

    init {
        getClients()
    }

    fun getClients() {
        _uiState.update { it.copy(dataState = ClientChoiceDataState.Loading) }

        viewModelScope.launch {
            when (val result = clientChoiceRepository.getClients()) {
                is ResultWrapper.Success -> {
                    val data = result.value
                    _uiState.update {
                        it.copy(dataState = ClientChoiceDataState.Success(data))
                    }
                }


                is ResultWrapper.Error -> {
                    when (val error = result.error) {
                        is AppError.NotFound -> {

                        }

                        is AppError.NoInternet -> {
                            _uiState.update { it.copy(dataState = ClientChoiceDataState.Error("Ошибка соединения")) }
                        }

                        is AppError.HttpError -> {
                            _uiState.update { it.copy(dataState = ClientChoiceDataState.Error("Ошибка сервера ${error.code}")) }
                        }

                        is AppError.Unknown -> {
                            _uiState.update { it.copy(dataState = ClientChoiceDataState.Error("Неизвестная ошибка: ${error.throwable.message}")) }
                        }
                    }
                }
            }
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        _uiState.update { it.copy(searchQuery = newQuery) }
    }

    fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index) }
    }

    fun onClickClientChoice(value: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            clientChoiceRepository.setClient(value)
            onComplete()
        }
    }
}