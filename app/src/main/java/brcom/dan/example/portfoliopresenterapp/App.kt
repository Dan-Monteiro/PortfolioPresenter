package brcom.dan.example.portfoliopresenterapp

import android.app.Application
import brcom.dan.example.portfoliopresenterapp.data.di.DataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

        DataModule.load()
    }
}