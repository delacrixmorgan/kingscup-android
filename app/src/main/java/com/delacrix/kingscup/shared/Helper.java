package com.delacrix.kingscup.shared;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

import com.delacrix.kingscup.R;

/**
 * Created by delacrix on 17/03/2017.
 */

public class Helper {
    public static void showFragment(Activity activity, Fragment fragment, String backStack) {
        activity.getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(backStack)
                .commit();
    }
}
