package com.example.companionapp.ui.sounds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.companionapp.R
import com.example.companionapp.data.ApiClient
import com.example.companionapp.data.ApiService
import com.example.companionapp.data.Sound
import com.example.companionapp.databinding.ActivitySoundsBinding
import com.example.companionapp.utilities.InjectorUtils
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

class SoundsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySoundsBinding

    private lateinit var soundsAdapter: SoundsAdapter

    private lateinit var soundsViewModel: SoundsViewModel

    private var disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sounds)

        ApiClient.initialize()
        initializeUi()
        soundsViewModel.refreshSounds()
    }

    override fun onDestroy() {
        disposables.clear()
        disposables.dispose()
        super.onDestroy()
    }

    private fun initializeUi() {
        soundsAdapter = SoundsAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SoundsActivity)
            adapter = soundsAdapter
        }

        val factory = InjectorUtils.provideSoundsViewModelFactory()
        soundsViewModel = ViewModelProviders.of(this, factory)
            .get(SoundsViewModel::class.java)

        soundsViewModel.sounds.observe(this, Observer { sounds ->
            val array = arrayListOf<Sound>()
            array.addAll(sounds)
            soundsAdapter.setSounds(array)
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            soundsViewModel.refreshSounds()
        }

        soundsViewModel.soundRefreshState.observe(this, Observer {
            binding.swipeRefreshLayout.isRefreshing = it
        })

        disposables.add(soundsAdapter.clickSubject.subscribe{soundsViewModel.handleClick(it)})
    }
}
