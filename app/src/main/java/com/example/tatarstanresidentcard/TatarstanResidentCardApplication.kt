package com.example.tatarstanresidentcard

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TatarstanResidentCardApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        MapKitFactory.setApiKey("1681f575-a055-4d87-9f68-9706c335baa0")
    }

}