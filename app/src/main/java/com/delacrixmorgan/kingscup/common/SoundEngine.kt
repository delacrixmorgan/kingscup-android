package com.delacrixmorgan.kingscup.common

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build

/**
 * Created by Delacrix Morgan on 28/12/2017.
 **/

class SoundEngine private constructor(context: Context) {
    companion object {
        @Volatile private lateinit var SoundEngineInstance: SoundEngine

        fun newInstance(context: Context): SoundEngine {
            this.SoundEngineInstance = SoundEngine(context)
            return this.SoundEngineInstance
        }

        fun getInstance(): SoundEngine = this.SoundEngineInstance
    }

    private lateinit var soundPool: SoundPool
    private lateinit var audioManager: AudioManager

    private var isLoaded = false

    init {
        this.buildSoundEngine(context)
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

    fun playSound(soundType: SoundType) {
        if (isLoaded) {
            val volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC).toFloat() / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC).toFloat()
            soundPool.play(soundType.resourceID, volume, volume, 1, 0, 1f)
        }
    }
}