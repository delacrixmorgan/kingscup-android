package com.delacrixmorgan.kingscup.game.dialog

import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.PreferenceHelper
import com.delacrixmorgan.kingscup.common.PreferenceHelper.get
import com.delacrixmorgan.kingscup.common.PreferenceHelper.set
import com.delacrixmorgan.kingscup.engine.SoundEngine
import com.delacrixmorgan.kingscup.engine.VibratorEngine
import com.delacrixmorgan.kingscup.model.SoundType
import com.delacrixmorgan.kingscup.model.VibrateType
import kotlinx.android.synthetic.main.fragmment_game_dialog.*

class GameDialogFragment : DialogFragment(), View.OnClickListener {

    companion object {
        fun create(listener: GameDialogListeners) = GameDialogFragment().apply {
            this.listener = listener
        }
    }

    private val preference: SharedPreferences by lazy {
        PreferenceHelper.getPreference(requireContext())
    }

    private val soundEngine by lazy {
        SoundEngine.getInstance(requireContext())
    }

    private var action = GameDialogActions.Resume

    private lateinit var listener: GameDialogListeners

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragmment_game_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val soundPreference = preference[PreferenceHelper.SOUND, PreferenceHelper.SOUND_DEFAULT]
        val vibratePreference =
            preference[PreferenceHelper.VIBRATE, PreferenceHelper.VIBRATE_DEFAULT]

        volumeButton.setImageResource(if (soundPreference) R.drawable.ic_volume_up else R.drawable.ic_volume_off)
        vibrateButton.setImageResource(if (vibratePreference) R.drawable.ic_vibration_enable else R.drawable.ic_vibration_disable)

        quitButton.setOnClickListener(this)
        volumeButton.setOnClickListener(this)
        resumeButton.setOnClickListener(this)
        vibrateButton.setOnClickListener(this)
        startNewGameButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val context = view.context

        when (view.id) {
            R.id.startNewGameButton -> {
                action = GameDialogActions.Restart
                soundEngine.playSound(context, SoundType.Whoosh)
                dismiss()
            }

            R.id.resumeButton -> {
                soundEngine.playSound(context, SoundType.Whoosh)
                action = GameDialogActions.Resume
                dismiss()
            }

            R.id.quitButton -> {
                action = GameDialogActions.Quit
                soundEngine.playSound(context, SoundType.Whoosh)
                dismiss()
            }

            R.id.vibrateButton -> {
                updateVibratePreference()
                VibratorEngine.vibrate(view, VibrateType.Short)
            }

            R.id.volumeButton -> {
                updateSoundPreference()
                soundEngine.playSound(context, SoundType.Click)
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        when (action) {
            GameDialogActions.Restart -> listener.onGameRestart()
            GameDialogActions.Resume -> listener.onGameResumed()
            GameDialogActions.Quit -> listener.onGameQuit()
        }
    }

    private fun updateVibratePreference() {
        val preference = PreferenceHelper.getPreference(requireContext())
        val vibratePreference =
            preference[PreferenceHelper.VIBRATE, PreferenceHelper.VIBRATE_DEFAULT]

        if (vibratePreference) {
            vibrateButton.setImageResource(R.drawable.ic_vibration_disable)
        } else {
            vibrateButton.setImageResource(R.drawable.ic_vibration_enable)
        }

        preference[PreferenceHelper.VIBRATE] = !vibratePreference
    }

    private fun updateSoundPreference() {
        val preference = PreferenceHelper.getPreference(requireContext())
        val soundPreference = preference[PreferenceHelper.SOUND, PreferenceHelper.SOUND_DEFAULT]

        if (soundPreference) {
            volumeButton.setImageResource(R.drawable.ic_volume_off)
        } else {
            volumeButton.setImageResource(R.drawable.ic_volume_up)
        }

        preference[PreferenceHelper.SOUND] = !soundPreference
    }
}