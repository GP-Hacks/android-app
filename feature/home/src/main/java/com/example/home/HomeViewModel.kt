package com.example.home

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.CheckAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val checkAuthUseCase: CheckAuthUseCase
): ViewModel() {

    fun checkAuth() = checkAuthUseCase()

}