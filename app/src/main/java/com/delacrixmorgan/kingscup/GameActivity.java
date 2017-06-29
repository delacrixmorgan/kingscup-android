package com.delacrixmorgan.kingscup;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.view.WindowManager;

import com.delacrixmorgan.kingscup.engine.GameEngine;
import com.delacrixmorgan.kingscup.fragment.GuideFragment;
import com.delacrixmorgan.kingscup.fragment.SelectFragment;
import com.delacrixmorgan.kingscup.shared.Helper;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        if (GameEngine.getInstance().getmKingCounter() < 1){
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
}
