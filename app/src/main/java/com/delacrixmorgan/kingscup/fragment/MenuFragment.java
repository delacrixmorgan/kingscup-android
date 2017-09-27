package com.delacrixmorgan.kingscup.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.delacrixmorgan.kingscup.GameActivity;
import com.delacrixmorgan.kingscup.R;
import com.delacrixmorgan.kingscup.engine.GameEngine;
import com.delacrixmorgan.kingscup.shared.Helper;

/**
 * Created by Delacrix Morgan on 09/10/2016.
 */

public class MenuFragment extends Fragment implements View.OnClickListener {

    private static String TAG = "MenuFragment";
    private Button mStartButton, mDeckButton, mSettingButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        mStartButton = (Button) rootView.findViewById(R.id.button_start);
        mDeckButton = (Button) rootView.findViewById(R.id.button_deck);
        mSettingButton = (Button) rootView.findViewById(R.id.button_setting);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GameEngine.newInstance(getActivity());

        mStartButton.setOnClickListener(this);
        mDeckButton.setOnClickListener(this);
        mSettingButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        GameEngine.getInstance().playSound(getActivity(), "BUTTON_CLICK");
        switch (v.getId()) {
            case R.id.button_start:
                GameEngine.newInstance(getActivity());
                startActivity(new Intent(getActivity(), GameActivity.class));
                break;

            case R.id.button_deck:
                Toast.makeText(getActivity(), getResources().getString(R.string.designer_vocation), Toast.LENGTH_SHORT).show();
                break;

            case R.id.button_setting:
                Helper.showFragment(getActivity(), new SettingFragment(), MenuFragment.TAG);
                break;
        }
    }
}
