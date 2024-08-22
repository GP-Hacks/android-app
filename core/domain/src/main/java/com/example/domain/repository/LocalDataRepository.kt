package com.example.domain.repository

interface LocalDataRepository {

    fun setEmailAuth(email: String)

    fun checkAuth(): Boolean

}