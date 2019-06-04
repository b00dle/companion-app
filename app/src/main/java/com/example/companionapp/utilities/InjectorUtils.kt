package com.example.companionapp.utilities

import com.example.companionapp.data.SoundRepository
import com.example.companionapp.ui.sounds.SoundsViewModelFactory

object InjectorUtils {

    fun provideSoundsViewModelFactory(): SoundsViewModelFactory {
        val soundRepository = SoundRepository.getInstance()
        return SoundsViewModelFactory(soundRepository)
    }
}