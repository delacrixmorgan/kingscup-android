package com.delacrixmorgan.kingscup.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.delacrixmorgan.kingscup.R;
import com.delacrixmorgan.kingscup.shared.Helper;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Delacrix Morgan on 04/03/2017.
 */

public class AboutFragment extends PreferenceFragment {
    private static String TAG = "AboutFragment";

    final int unicode[] = {0x1F648, 0x1F649, 0x1F64A};
    private SwitchPreference mQuickGuide;
    private Preference mCreditLibrary, mVersion;
    private int mMonkeyCounter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        mQuickGuide = (SwitchPreference) findPreference("quick_guide");

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

                return true;
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

                ImageView jenkinsImageView = (ImageView) creditDialog.findViewById(R.id.iv_jenkins_github);
                jenkinsImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://github.com/chrisjenx/Calligraphy"));
                        startActivity(intent);
                    }
                });

                ImageView spartanImageView = (ImageView) creditDialog.findViewById(R.id.iv_spartan_github);
                spartanImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://github.com/theleagueof/league-spartan"));
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
