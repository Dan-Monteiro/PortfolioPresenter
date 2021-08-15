package brcom.dan.example.portfoliopresenterapp.presentation.di

import brcom.dan.example.portfoliopresenterapp.presentation.MainViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModule {

    fun load(){
        loadKoinModules(viewModelModule())
    }

    private fun viewModelModule(): Module {
        return module {
            factory {
                MainViewModel(get())
            }
        }
    }
}