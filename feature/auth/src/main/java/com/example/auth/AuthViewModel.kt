package com.example.auth

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.SetEmailAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val setEmailAuthUseCase: SetEmailAuthUseCase
): ViewModel() {

    fun setEmailAuth(email: String) {
        setEmailAuthUseCase(email)
    }

}