package com.delacrixmorgan.kingscup;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.delacrixmorgan.kingscup.engine.GameEngine;
import com.delacrixmorgan.kingscup.fragment.GuideFragment;

/**
 * Created by Delacrix Morgan on 26/03/2017.
 */

public class GameActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, new GuideFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (GameEngine.getInstance().getmKingCounter() < 1) {
            finish();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.quit_title)
                    .setMessage(R.string.quit_header)
                    .setPositiveButton(R.string.quit_yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            GameEngine.getInstance().playSound(getApplicationContext(), "CARD_WHOOSH");
                            finish();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton(R.string.quit_no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            GameEngine.getInstance().playSound(getApplicationContext(), "CARD_WHOOSH");
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
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
