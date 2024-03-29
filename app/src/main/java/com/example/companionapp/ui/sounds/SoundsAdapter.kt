package com.example.companionapp.ui.sounds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.companionapp.data.Sound
import com.example.companionapp.databinding.SoundViewBinding
import io.reactivex.subjects.PublishSubject
import com.jakewharton.rxbinding3.view.clicks

class SoundsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected val sounds = ArrayList<Sound>()

    var clickSubject = PublishSubject.create<SoundViewHolder>()

    class SoundViewHolder(var binding: SoundViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SoundViewHolder(SoundViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SoundViewHolder -> {
                val sound = sounds[holder.adapterPosition]
                holder.binding.myId.text = sound.id.toString()
                holder.binding.name.text = sound.name
                holder.binding.uuid.text = sound.uuid

                holder.binding.photo.clicks()
                    .map { holder as SoundViewHolder }
                    .subscribe(clickSubject)
            }
        }
    }

    override fun getItemCount() = sounds.size

    // ---------------------

    fun clearSounds() {
        this.sounds.clear()
        notifyDataSetChanged()
    }

    fun setSounds(sounds: ArrayList<Sound>) {
        this.sounds.clear()
        this.sounds.addAll(sounds)
        notifyDataSetChanged()
    }

    // ---------------------
}