package com.delacrixmorgan.kingscup.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.delacrixmorgan.kingscup.R;
import com.delacrixmorgan.kingscup.adapter.CardAdapter;
import com.delacrixmorgan.kingscup.engine.GameEngine;

/**
 * Created by Delacrix Morgan on 04/03/2017.
 */

public class SelectFragment extends Fragment {
    private static String TAG = "SelectFragment";

    private RecyclerView mCardRecyclerView;
    private CardAdapter mCardAdapter;
    private Button mButtonEndGame;
    private ImageView mImageVolume;
    private TextView mTextStatusHeader, mTextStatusBody;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select, container, false);

        mCardRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_select_card_recycler_view);
        mButtonEndGame = (Button) rootView.findViewById(R.id.button_endgame);

        mImageVolume = (ImageView) rootView.findViewById(R.id.image_cup_volume);

        mTextStatusHeader = (TextView) rootView.findViewById(R.id.status_header);
        mTextStatusBody = (TextView) rootView.findViewById(R.id.status_body);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCardAdapter = new CardAdapter(getActivity());

        mCardRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mCardRecyclerView.setAdapter(mCardAdapter);
        mCardRecyclerView.scrollToPosition(0);
    }

    @Override
    public void onResume() {
        super.onResume();

        mCardRecyclerView.setEnabled(true);

        if (GameEngine.getInstance().checkWin(mCardAdapter)) {
            GameEngine.getInstance().stopGame(mCardAdapter);

            Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(2000);

            mButtonEndGame.setVisibility(View.VISIBLE);
            mButtonEndGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        }

        switch (GameEngine.getInstance().getmKingCounter()) {
            case 0:
                mImageVolume.setBackgroundResource(R.drawable.cup_volume_4);
                mTextStatusBody.setText(R.string.game_over_header);
                mTextStatusHeader.setText(R.string.game_over_body);
                break;

            case 1:
                mImageVolume.setBackgroundResource(R.drawable.cup_volume_3);
                mTextStatusBody.setText(GameEngine.getInstance().getmKingCounter() + " " + getString(R.string.counter_king_left));
                mTextStatusHeader.setText(GameEngine.getInstance().getNextText());
                break;

            case 2:
                mImageVolume.setBackgroundResource(R.drawable.cup_volume_2);
                mTextStatusBody.setText(GameEngine.getInstance().getmKingCounter() + " " + getString(R.string.counter_kings_left));
                mTextStatusHeader.setText(GameEngine.getInstance().getNextText());
                break;

            case 3:
                mImageVolume.setBackgroundResource(R.drawable.cup_volume_1);
                mTextStatusBody.setText(GameEngine.getInstance().getmKingCounter() + " " + getString(R.string.counter_kings_left));
                mTextStatusHeader.setText(GameEngine.getInstance().getNextText());
                break;

            default:
                mImageVolume.setBackgroundResource(R.drawable.cup_whole);
                mTextStatusBody.setText(GameEngine.getInstance().getmKingCounter() + " " + getString(R.string.counter_kings_left));
                mTextStatusHeader.setText(GameEngine.getInstance().getNextText());
                break;
        }
    }
}
