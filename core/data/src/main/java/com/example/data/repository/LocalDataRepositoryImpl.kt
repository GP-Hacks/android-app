package com.example.data.repository

import com.example.data.source.local.SharedPreferenceLocalSource
import com.example.domain.repository.LocalDataRepository
import javax.inject.Inject

class LocalDataRepositoryImpl @Inject constructor(
    private val sharedPreferenceLocalSource: SharedPreferenceLocalSource
): LocalDataRepository {
    override fun setEmailAuth(email: String) {
        sharedPreferenceLocalSource.setEmail(email)
    }

    override fun checkAuth(): Boolean = sharedPreferenceLocalSource.getEmail() != null
}