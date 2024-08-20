package com.example.data.di

import com.example.data.repository.ApiRepositoryImpl
import com.example.data.source.remote.ApiRemoteSource
import com.example.domain.repository.ApiRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindApiRepository(impl: ApiRepositoryImpl): ApiRepository

    companion object {
        @Provides
        fun provideApiRemoteSource(): ApiRemoteSource = ApiRemoteSource()
    }

}