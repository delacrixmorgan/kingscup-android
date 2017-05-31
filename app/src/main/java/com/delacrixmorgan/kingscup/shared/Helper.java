package com.delacrixmorgan.kingscup.shared;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.transition.Slide;
import android.view.Gravity;

import com.delacrixmorgan.kingscup.R;

/**
 * Created by Delacrix Morgan on 17/03/2017.
 */

public class Helper {
    public static void showFragment(Activity activity, Fragment fragment, String backStack) {
        activity.getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, fragment, fragment.getClass().getSimpleName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(backStack)
                .commit();
    }

    public static void showAddFragmentSlideDown(Activity activity, Fragment fragment, String backStack) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            fragment.setEnterTransition(new Slide(Gravity.BOTTOM).setDuration(200));

            activity.getFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_main_vg_fragment, fragment)
                    .addToBackStack(backStack)
                    .commit();
        } else {
            activity.getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_main_vg_fragment, fragment, fragment.getClass().getSimpleName())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(backStack)
                    .commit();
        }
    }
}
