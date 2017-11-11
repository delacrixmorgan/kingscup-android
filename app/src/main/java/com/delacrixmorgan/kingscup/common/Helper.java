package com.delacrixmorgan.kingscup.common;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.transition.Slide;
import android.view.Gravity;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

import com.delacrixmorgan.kingscup.R;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Delacrix Morgan on 17/03/2017.
 */

public class Helper {
    final public static String SHARED_PREFERENCE = "SHARED_PREFERENCE";
    final public static String QUICK_GUIDE_PREFERENCE = "QUICK_GUIDE_PREFERENCE";
    final public static String SOUND_EFFECTS_PREFERENCE = "SOUND_EFFECTS_PREFERENCE";
    final public static String LANGUAGE_PREFERENCE = "LANGUAGE_PREFERENCE";
    final public static CharSequence[] mLanguageItems = {"Default Language", "English", "简体中文"};

    public static void showFragment(Activity activity, Fragment fragment) {
        activity.getFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, fragment.getClass().getSimpleName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    public static void showAddFragmentSlideDown(Activity activity, Fragment fragment) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            fragment.setEnterTransition(new Slide(Gravity.BOTTOM).setDuration(200));

            activity.getFragmentManager()
                    .beginTransaction()
                    .add(R.id.mainContainer, fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    public static void animateButtonGrow(Activity activity, FloatingActionButton button) {
        AnimationSet animGrow = new AnimationSet(true);
        animGrow.addAnimation(AnimationUtils.loadAnimation(activity, R.anim.pop_out));
        button.startAnimation(animGrow);
    }

    public static void setLocaleLanguage(Context context) {
        String languageCode = Locale.getDefault().getLanguage();

        switch (context.getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE).getInt(Helper.LANGUAGE_PREFERENCE, 1)) {
            case 1:
                languageCode = "en";
                break;

            case 2:
                languageCode = "zh";
                break;
        }

        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.locale = new Locale(languageCode);
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }
}
