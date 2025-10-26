package ru.xaori.schedule.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.xaori.schedule.core.UIState
import ru.xaori.schedule.domain.usecase.SubscribeToTopicUseCase


//class NotificationViewModel(
//    private val subscribeToTopicUseCase: SubscribeToTopicUseCase
//) : ViewModel() {
//
//    private val _subscriptionState = MutableStateFlow<UIState>(UIState.Idle)
//    val subscriptionState: StateFlow<UIState> = _subscriptionState
//
//    fun subscribe(topic: String) {
//        viewModelScope.launch {
//            _subscriptionState.value = UIState.Loading
//            val result = subscribeToTopicUseCase(topic)
//            _subscriptionState.value = if (result.isSuccess) {
//                UIState.Success
//            } else {
//                UIState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
//            }
//        }
//    }
//}
