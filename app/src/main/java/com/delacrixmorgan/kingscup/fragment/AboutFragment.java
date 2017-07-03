package com.delacrixmorgan.kingscup.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.delacrixmorgan.kingscup.MainActivity;
import com.delacrixmorgan.kingscup.R;
import com.delacrixmorgan.kingscup.engine.GameEngine;
import com.delacrixmorgan.kingscup.shared.Helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Delacrix Morgan on 04/03/2017.
 */

public class AboutFragment extends PreferenceFragment {
    private static String TAG = "AboutFragment";

    final int unicode[] = {0x1F648, 0x1F649, 0x1F64A};
    private SwitchPreference mQuickGuide, mSoundEffects;
    private Preference mLanguage, mCreditLibrary, mVersion;
    private int mMonkeyCounter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        mQuickGuide = (SwitchPreference) findPreference("quick_guide");
        mSoundEffects = (SwitchPreference) findPreference("sound_effects");

        mLanguage = findPreference("language");
        mCreditLibrary = findPreference("credit_library");
        mVersion = findPreference("version_number");

        mMonkeyCounter = 0;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mQuickGuide.setChecked(getActivity().getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).getBoolean(Helper.QUICK_GUIDE_PREFERENCE, true));
        mQuickGuide.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).edit();
                editor.putBoolean(Helper.QUICK_GUIDE_PREFERENCE, (Boolean) newValue);
                editor.apply();
                GameEngine.getInstance().playSound(getActivity(), "BUTTON_CLICK");
                return true;
            }
        });

        mSoundEffects.setChecked(getActivity().getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).getBoolean(Helper.SOUND_EFFECTS_PREFERENCE, true));
        mSoundEffects.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).edit();
                editor.putBoolean(Helper.SOUND_EFFECTS_PREFERENCE, (Boolean) newValue);
                editor.apply();
                GameEngine.getInstance().playSound(getActivity(), "BUTTON_CLICK");
                return true;
            }
        });

        mLanguage.setSummary(Helper.mLanguageItems[getActivity().getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).getInt(Helper.LANGUAGE_PREFERENCE, 0)]);
        mLanguage.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Language")
                        .setSingleChoiceItems(Helper.mLanguageItems, getActivity().getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).getInt(Helper.LANGUAGE_PREFERENCE, 0),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        SharedPreferences.Editor editor = getActivity().getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).edit();
                                        editor.putInt(Helper.LANGUAGE_PREFERENCE, item);
                                        editor.apply();

                                        mLanguage.setSummary(Helper.mLanguageItems[item]);
                                        Helper.setLocaleLanguage(getActivity());

                                        Intent refresh = new Intent(getActivity(), MainActivity.class);
                                        getActivity().finish();
                                        startActivity(refresh);
                                    }
                                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        })
                        .show();

                return false;
            }
        });


        mCreditLibrary.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                final Dialog creditDialog = new Dialog(getActivity());

                creditDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                creditDialog.setContentView(R.layout.view_credit);
                creditDialog.setTitle("Credits");

                creditDialog.show();

                ImageView spartanImageView = (ImageView) creditDialog.findViewById(R.id.iv_spartan_github);
                spartanImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://github.com/theleagueof/league-spartan"));
                        startActivity(intent);
                    }
                });

                ImageView jenkinsImageView = (ImageView) creditDialog.findViewById(R.id.iv_jenkins_github);
                jenkinsImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://github.com/chrisjenx/Calligraphy"));
                        startActivity(intent);
                    }
                });

                ImageView balysImageView= (ImageView) creditDialog.findViewById(R.id.iv_balys_github);
                balysImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://github.com/balysv/material-ripple"));
                        startActivity(intent);
                    }
                });

                Button doneButton = (Button) creditDialog.findViewById(R.id.button_done);
                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        creditDialog.dismiss();
                    }
                });

                return false;
            }
        });

        try {
            mVersion.setSummary(getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName);
            mVersion.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    mMonkeyCounter = mMonkeyCounter > 2 ? 0 : mMonkeyCounter;
                    Toast.makeText(getActivity(), new String(Character.toChars(unicode[mMonkeyCounter++])), Toast.LENGTH_SHORT).show();

                    return false;
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
