package com.example.companionapp.data

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SoundRepository private constructor(){

    fun getSounds(): Single<List<Sound>> {
        return ApiClient.getInterface().fetchAllSounds()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    companion object {
        @Volatile private var instance: SoundRepository? = null

        fun getInstance() =
                instance ?: synchronized(this) {
                    instance ?: SoundRepository().also { instance = it }
                }
    }
}