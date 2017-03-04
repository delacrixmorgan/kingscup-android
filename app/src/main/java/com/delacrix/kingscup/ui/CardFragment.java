package com.delacrix.kingscup.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.delacrix.kingscup.GameEngine;
import com.delacrix.kingscup.R;

/**
 * Created by Delacrix on 09/10/2016.
 */

public class CardFragment extends Fragment {

    private TextView mDeckAction;
    private Button mNextButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);

        mDeckAction = (TextView) rootView.findViewById(R.id.deck_action);
        mNextButton = (Button) rootView.findViewById(R.id.button_next);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeckAction.setText(GameEngine.getInstance(getActivity()).drawCard().getmAction());
            }
        });
    }

}
