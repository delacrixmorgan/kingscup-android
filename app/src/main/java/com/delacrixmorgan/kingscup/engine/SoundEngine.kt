package com.delacrixmorgan.kingscup.engine

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import com.delacrixmorgan.kingscup.common.PreferenceHelper
import com.delacrixmorgan.kingscup.common.PreferenceHelper.get
import com.delacrixmorgan.kingscup.model.SoundType

class SoundEngine private constructor(context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: SoundEngine? = null

        fun getInstance(context: Context): SoundEngine {
            return INSTANCE
                ?: synchronized(this) {
                    SoundEngine(context).also {
                        INSTANCE = it
                    }
                }
        }
    }

    private var isLoaded = false
    private val soundPool: SoundPool
    private val audioManager: AudioManager by lazy {
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    init {
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

        soundPool.setOnLoadCompleteListener { _, _, _ ->
            this.isLoaded = true
        }

        SoundType.values().forEach {
            it.resourceID = soundPool.load(context, it.rawID, 1)
        }
    }

    fun playSound(context: Context, soundType: SoundType) {
        val preference = PreferenceHelper.getPreference(context)
        val soundPreference = preference[PreferenceHelper.SOUND, PreferenceHelper.SOUND_DEFAULT]

        if (isLoaded && soundPreference) {
            val volume = audioManager.getStreamVolume(
                AudioManager.STREAM_MUSIC
            ).toFloat() / audioManager.getStreamMaxVolume(
                AudioManager.STREAM_MUSIC
            ).toFloat()
            soundPool.play(soundType.resourceID, volume, volume, 1, 0, 1F)
        }
    }
}