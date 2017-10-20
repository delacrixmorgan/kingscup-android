package com.delacrixmorgan.kingscup;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import com.delacrixmorgan.kingscup.engine.GameEngine;
import com.delacrixmorgan.kingscup.fragment.MenuFragment;
import com.delacrixmorgan.kingscup.shared.Helper;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Delacrix Morgan on 26/03/2017.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Helper.setLocaleLanguage(this);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, new MenuFragment())
                .commit();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = getTheme();
            theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
            int color = typedValue.data;

            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.kingscup_logo_icon);
            setTaskDescription(new ActivityManager.TaskDescription(null, bm, color));
            bm.recycle();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        GameEngine.getInstance().playSound(this, "CARD_WHOOSH");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
