package com.example.finalproject

import android.app.Application
import com.example.finalproject.data.AppContainer
import com.example.finalproject.data.AppDataContainer

class TabelaApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}