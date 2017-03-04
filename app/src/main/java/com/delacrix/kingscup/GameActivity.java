package com.delacrix.kingscup;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.delacrix.kingscup.ui.MenuFragment;

/**
 * Created by delac on 04/03/2017.
 */

public class GameActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, new MenuFragment())
                .commit();
    }
}
