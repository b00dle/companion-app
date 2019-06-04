package com.example.companionapp.ui.sounds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.companionapp.data.SoundRepository

class SoundsViewModelFactory(private val soundRepository: SoundRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SoundsViewModel(soundRepository) as T
    }
}