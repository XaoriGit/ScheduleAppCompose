package ru.xaori.schedule.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.xaori.schedule.domain.usecase.IsClientUseCase

class NavViewModel(private val isClientUseCase: IsClientUseCase) : ViewModel() {
    private val _newUser = MutableStateFlow<Boolean?>(null)
    val newUser: StateFlow<Boolean?> = _newUser

    init {
        viewModelScope.launch {
            _newUser.value = !isClientUseCase.invoke()
        }
    }
}