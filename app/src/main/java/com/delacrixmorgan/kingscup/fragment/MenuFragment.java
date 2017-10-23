package com.delacrixmorgan.kingscup.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.delacrixmorgan.kingscup.GameActivity;
import com.delacrixmorgan.kingscup.R;
import com.delacrixmorgan.kingscup.engine.GameEngine;
import com.delacrixmorgan.kingscup.shared.Helper;

/**
 * Created by Delacrix Morgan on 09/10/2016.
 */

public class MenuFragment extends Fragment implements View.OnClickListener {

    private static String TAG = "MenuFragment";
    private FloatingActionButton mRateButton, mStartButton, mSettingButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        mRateButton = rootView.findViewById(R.id.fragment_menu_rate_button);
        mStartButton = rootView.findViewById(R.id.fragment_menu_start_button);
        mSettingButton = rootView.findViewById(R.id.fragment_menu_setting_button);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GameEngine.newInstance(getActivity());

        mRateButton.setOnClickListener(this);
        mStartButton.setOnClickListener(this);
        mSettingButton.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        mRateButton.setEnabled(true);
        mStartButton.setEnabled(true);
        mSettingButton.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_menu_rate_button:
                mRateButton.setEnabled(false);
                break;

            case R.id.fragment_menu_start_button:
                mStartButton.setEnabled(false);
                GameEngine.newInstance(getActivity());
                startActivity(new Intent(getActivity(), GameActivity.class));
                break;

            case R.id.fragment_menu_setting_button:
                mSettingButton.setEnabled(false);
                Helper.showFragment(getActivity(), new SettingFragment(), MenuFragment.TAG);
                break;
        }
    }
}
