package com.example.finalproject

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

object AppViewModelProvider {
    val Factory = viewModelFactory {


        initializer {
            MainViewModel()
        }

        initializer {
            LoginViewModel(tabelaApplication().container.userRepository)
        }

        initializer {
            ProfileViewModel(
                tabelaApplication().container.userRepository,
                this.createSavedStateHandle()
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [PhonebookApplication].
 */
fun CreationExtras.tabelaApplication(): TabelaApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TabelaApplication)