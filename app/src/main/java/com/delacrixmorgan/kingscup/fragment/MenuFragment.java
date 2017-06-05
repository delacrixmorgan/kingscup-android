package com.delacrixmorgan.kingscup.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
    private Button mStartButton, mDeckButton, mAboutButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        mStartButton = (Button) rootView.findViewById(R.id.button_start);
        mDeckButton = (Button) rootView.findViewById(R.id.button_deck);
        mAboutButton = (Button) rootView.findViewById(R.id.button_about);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mStartButton.setOnClickListener(this);
        mDeckButton.setOnClickListener(this);
        mAboutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start:
                GameEngine.newInstance(getActivity());
                startActivity(new Intent(getActivity(), GameActivity.class));
                break;

            case R.id.button_deck:
                Toast.makeText(getActivity(), "Designer on Vacation", Toast.LENGTH_SHORT).show();
                break;

            case R.id.button_about:
                Helper.showFragment(getActivity(), new AboutFragment(), MenuFragment.TAG);
                break;
        }
    }
}
