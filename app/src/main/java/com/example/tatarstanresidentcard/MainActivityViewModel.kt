package com.example.tatarstanresidentcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.SendDeviceTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val sendDeviceTokenUseCase: SendDeviceTokenUseCase
): ViewModel() {

    private val _uiState: MutableStateFlow<MainActivityUiState> = MutableStateFlow(MainActivityUiState.Loading)
    val uiState: StateFlow<MainActivityUiState>
        get() = _uiState

    init {
        viewModelScope.launch {
            delay(5000)
            _uiState.value = MainActivityUiState.Success
        }
        sendDeviceToken()
    }

    private fun sendDeviceToken() {
        viewModelScope.launch {
            sendDeviceTokenUseCase()
        }
    }

}

sealed interface MainActivityUiState {
    data object Loading: MainActivityUiState
    data object Success: MainActivityUiState
}