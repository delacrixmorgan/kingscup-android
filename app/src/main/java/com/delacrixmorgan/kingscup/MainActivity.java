package com.delacrixmorgan.kingscup;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.delacrixmorgan.kingscup.engine.GameEngine;
import com.delacrixmorgan.kingscup.fragment.MenuFragment;

/**
 * Created by Delacrix Morgan on 26/03/2017.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GameEngine.newInstance(this);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, new MenuFragment())
                .commit();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}
