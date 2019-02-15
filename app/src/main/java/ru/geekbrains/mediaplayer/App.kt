package ru.geekbrains.mediaplayer

import android.app.Application
import org.koin.android.ext.android.startKoin
import ru.geekbrains.mediaplayer.di.dataSourceModule
import ru.geekbrains.mediaplayer.di.mainModule
import ru.geekbrains.mediaplayer.di.viewModelModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(mainModule, viewModelModule, dataSourceModule))
    }
}