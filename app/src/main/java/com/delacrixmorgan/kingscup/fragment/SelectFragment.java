package com.delacrixmorgan.kingscup.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.delacrixmorgan.kingscup.R;
import com.delacrixmorgan.kingscup.engine.GameEngine;

/**
 * Created by Delacrix Morgan on 04/03/2017.
 */

public class SelectFragment extends Fragment {
    private static String TAG = "SelectFragment";

    private RecyclerView mCardRecyclerView;
    private Button mButtonEndGame;
    private TextView mTextStatusHeader, mTextStatusBody;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select, container, false);

        mCardRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_select_card_recycler_view);
        mButtonEndGame = (Button) rootView.findViewById(R.id.button_endgame);

        mTextStatusHeader = (TextView) rootView.findViewById(R.id.status_header);
        mTextStatusBody = (TextView) rootView.findViewById(R.id.status_body);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTextStatusHeader.setText("Come On");
        mTextStatusBody.setText(GameEngine.getInstance().getmKingCounter() + " Kings Left");

        if (GameEngine.getInstance().checkWin()) {
            GameEngine.getInstance().stopGame();

            mButtonEndGame.setVisibility(View.VISIBLE);
            mButtonEndGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        }

        GameEngine.getInstance().initRecyclerView(getActivity(), mCardRecyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();

        mTextStatusHeader.setText("Come On");
        mTextStatusBody.setText(GameEngine.getInstance().getmKingCounter() + " Kings Left");
    }
}
