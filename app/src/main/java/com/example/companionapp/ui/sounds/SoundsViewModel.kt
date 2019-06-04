package com.example.companionapp.ui.sounds

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.companionapp.data.ApiClient
import com.example.companionapp.data.Sound
import com.example.companionapp.data.SoundRepository
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SoundsViewModel(private val soundsRepository: SoundRepository): ViewModel() {

    var soundRefreshState = MutableLiveData<Boolean>()
    var sounds = MutableLiveData<List<Sound>>()
    private var disposables = CompositeDisposable()

    fun refreshSounds() {
        disposables.add(soundsRepository.getSounds()
            .doFinally{soundRefreshState.value = false}
            .subscribe(
                { newSounds ->
                    sounds.postValue(newSounds)
                },
                { throwable ->
                    throw (throwable)
                }))
    }

    override fun onCleared() {
        disposables.clear()
        disposables.dispose()
    }
}