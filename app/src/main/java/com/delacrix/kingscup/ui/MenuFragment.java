package com.delacrix.kingscup.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.delacrix.kingscup.R;

/**
 * Created by Delacrix on 09/10/2016.
 */

public class MenuFragment extends Fragment {

    private Button mStartButton, mSettingsButton, mAboutButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        mStartButton = (Button) rootView.findViewById(R.id.button_startgame);
        mSettingsButton = (Button) rootView.findViewById(R.id.button_settings);
        mAboutButton = (Button) rootView.findViewById(R.id.button_about);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuFragment.this.showGameFragment();
            }
        });
    }

    public void showGameFragment() {
        getActivity().getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, new CardFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack("CardFragment")
                .commit();
    }


}
