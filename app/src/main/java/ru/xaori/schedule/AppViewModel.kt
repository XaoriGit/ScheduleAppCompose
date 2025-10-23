package ru.xaori.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.xaori.schedule.data.storage.ClientChoiceStorage
import ru.xaori.schedule.presentation.state.AppUiState

class AppViewModel(private val clientChoiceStorage: ClientChoiceStorage) : ViewModel() {

    private val _uiState = MutableStateFlow<AppUiState>(AppUiState.Loading)
    val uiState: StateFlow<AppUiState> = _uiState

    init {
        viewModelScope.launch {
            val client = clientChoiceStorage.getClient()
            _uiState.value = if (client.isNullOrEmpty()) AppUiState.NewUser else AppUiState.Success
        }
    }
}