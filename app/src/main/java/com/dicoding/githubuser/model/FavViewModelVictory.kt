package com.dicoding.githubuser.model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavViewModelFactory(private val application: Application) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: FavViewModelFactory? = null

        fun getInstance(application: Application): FavViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavViewModelFactory(application).also { INSTANCE = it }
            }
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavViewModel::class.java)) {
            return FavViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}