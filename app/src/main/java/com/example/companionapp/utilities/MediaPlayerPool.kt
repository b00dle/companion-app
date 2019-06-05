package com.example.companionapp.utilities

import android.media.MediaPlayer

class MediaPlayerPool(maxStreams: Int) {
    private val mediaPlayerPool = mutableListOf<MediaPlayer>().also{
        for (i in 0..maxStreams) it += buildPlayer()
    }

    private val playersInUse = mutableMapOf<String, MediaPlayer>()

    private fun buildPlayer() = MediaPlayer().apply {
        setOnPreparedListener { start() }
        setOnCompletionListener {  }
    }

    fun requestPlayer(uuid: String): MediaPlayer? {
        return if (!mediaPlayerPool.isEmpty() and !playersInUse.containsKey(uuid)) {
            mediaPlayerPool.removeAt(0).also {
                playersInUse[uuid] = it
            }
        } else if (playersInUse.containsKey(uuid)) {
            return playersInUse[uuid]
        }
        else null
    }

    fun recyclePlayer(uuid: String): Boolean {
        if(!playersInUse.contains(uuid)) {
            return false
        }
        val mediaPlayer = playersInUse[uuid]!!
        mediaPlayer.reset()
        playersInUse.remove(uuid).also {
            mediaPlayerPool += mediaPlayer
        }
        return true
    }

}