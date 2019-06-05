package com.example.companionapp.ui.sounds

import android.media.MediaPlayer
import android.util.Log
import android.util.Log.d
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.companionapp.R
import com.example.companionapp.data.ApiClient
import com.example.companionapp.data.Sound
import com.example.companionapp.data.SoundRepository
import com.example.companionapp.utilities.MediaPlayerPool
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SoundsViewModel(private val soundsRepository: SoundRepository): ViewModel() {

    var soundRefreshState = MutableLiveData<Boolean>()

    var sounds = MutableLiveData<List<Sound>>()

    private var disposables = CompositeDisposable()

    private var playerPool = MediaPlayerPool(16)

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

    fun handleClick(viewHolder: SoundsAdapter.SoundViewHolder) {
        viewHolder.binding.photo.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp)
        d("b00dle", "handling sound click for ${viewHolder.binding.uuid.text}")
    }

    fun init(sound: Sound) {
        val player = playerPool.requestPlayer(sound.uuid)
        player?.run {
            if(!isPlaying) {
                setDataSource("http://10.0.2.2:5000/api/${sound.uuid}")
                prepareAsync()
            }
        }
    }

    fun play(sound: Sound) {
        val player = playerPool.requestPlayer(sound.uuid)
        player?.run {
            if(!isPlaying) {
                start()
            }
        }
    }

    fun pause(sound: Sound) {
        val player = playerPool.requestPlayer(sound.uuid)
        player?.run {
            if(isPlaying) {
                pause()
            }
        }
    }

    fun stop(sound: Sound) {
        playerPool.recyclePlayer(sound.uuid)
    }

    override fun onCleared() {
        disposables.clear()
        disposables.dispose()
    }
}