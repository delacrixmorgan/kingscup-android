package com.delacrixmorgan.kingscup.common

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import com.delacrixmorgan.kingscup.common.PreferenceHelper.get
import com.delacrixmorgan.kingscup.model.SoundType

/**
 * SoundEngine
 * kingscup-android
 *
 * Created by Delacrix Morgan on 25/03/2018.
 * Copyright (c) 2018 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

class SoundEngine private constructor(context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: SoundEngine? = null

        fun getInstance(context: Context): SoundEngine {
            return INSTANCE ?: synchronized(this) {
                SoundEngine(context).also {
                    INSTANCE = it
                }
            }
        }
    }

    private var isLoaded = false
    private lateinit var soundPool: SoundPool
    private lateinit var audioManager: AudioManager

    init {
        buildSoundEngine(context)
    }

    @SuppressLint("NewApi")
    private fun buildSoundEngine(context: Context) {
        this.audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        this.soundPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()

            SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(5).build()
        } else {
            SoundPool(5, AudioManager.STREAM_MUSIC, 0)
        }

        this.soundPool.setOnLoadCompleteListener { _, _, _ ->
            this.isLoaded = true
        }

        SoundType.values().forEach {
            it.resourceID = this.soundPool.load(context, it.rawID, 1)
        }
    }

    fun playSound(context: Context, soundType: SoundType) {
        val preference = PreferenceHelper.getPreference(context)
        val soundPreference = preference[PreferenceHelper.SOUND, PreferenceHelper.SOUND_DEFAULT]

        if (this.isLoaded && soundPreference) {
            val volume = this.audioManager.getStreamVolume(AudioManager.STREAM_MUSIC).toFloat() / this.audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC).toFloat()
            this.soundPool.play(soundType.resourceID, volume, volume, 1, 0, 1f)
        }
    }
}