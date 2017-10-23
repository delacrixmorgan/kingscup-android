package com.delacrixmorgan.kingscup.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.delacrixmorgan.kingscup.R;
import com.delacrixmorgan.kingscup.engine.GameEngine;
import com.delacrixmorgan.kingscup.model.Card;
import com.delacrixmorgan.kingscup.shared.Helper;

/**
 * Created by Delacrix Morgan on 09/10/2016.
 */

public class CardFragment extends Fragment implements View.OnTouchListener {
    private static String TAG = "CardFragment";

    private TextView mTextName, mTextAction, mTextLightValue, mTextDarkValue;
    private ImageView mLightLargeSymbol, mLightSmallSymbol, mDarkSmallSymbol;
    private FloatingActionButton mDoneButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_card, container, false);

        mTextName = rootView.findViewById(R.id.card_name);
        mTextAction = rootView.findViewById(R.id.card_action);
        mTextLightValue = rootView.findViewById(R.id.card_light_value);
        mTextDarkValue = rootView.findViewById(R.id.card_dark_value);

        mLightLargeSymbol = rootView.findViewById(R.id.card_light_lg_symbol);
        mLightSmallSymbol = rootView.findViewById(R.id.card_light_sm_symbol);
        mDarkSmallSymbol = rootView.findViewById(R.id.card_dark_sm_symbol);

        mDoneButton = rootView.findViewById(R.id.fragment_card_done_button);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Card card = GameEngine.getInstance().getCurrentCard();

        Helper.animateButtonGrow(getActivity(), mDoneButton);

        mTextName.setText(card.getmName());
        mTextAction.setText(card.getmAction());
        mTextLightValue.setText(card.getmValue());
        mTextDarkValue.setText(card.getmValue());

        switch (card.getmSuit()) {
            case "Spade":
                mLightLargeSymbol.setBackgroundResource(R.drawable.spade_pink);
                mLightSmallSymbol.setBackgroundResource(R.drawable.spade_pink);
                mDarkSmallSymbol.setBackgroundResource(R.drawable.spade_dark);
                break;

            case "Heart":
                mLightLargeSymbol.setBackgroundResource(R.drawable.heart_pink);
                mLightSmallSymbol.setBackgroundResource(R.drawable.heart_pink);
                mDarkSmallSymbol.setBackgroundResource(R.drawable.heart_dark);
                break;

            case "Club":
                mLightLargeSymbol.setBackgroundResource(R.drawable.club_pink);
                mLightSmallSymbol.setBackgroundResource(R.drawable.club_pink);
                mDarkSmallSymbol.setBackgroundResource(R.drawable.club_dark);
                break;

            case "Diamond":
                mLightLargeSymbol.setBackgroundResource(R.drawable.diamond_pink);
                mLightSmallSymbol.setBackgroundResource(R.drawable.diamond_pink);
                mDarkSmallSymbol.setBackgroundResource(R.drawable.diamond_dark);
                break;
        }


        if (GameEngine.getInstance().getmKingCounter() < 1) {
            Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(2000);

            mDoneButton.setVisibility(View.GONE);
            mDoneButton.setEnabled(false);

            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    AnimationSet animGrow = new AnimationSet(true);
                    animGrow.addAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.pop_out));
                    mLightLargeSymbol.startAnimation(animGrow);
                }
            });

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    GameEngine.getInstance().playSound(getActivity(), "GAME_OVER");
                    SelectFragment fragment = (SelectFragment) getFragmentManager().findFragmentByTag("SelectFragment");
                    fragment.updateFragment();
                    getFragmentManager().popBackStack();

                }
            }, 2000);

        } else {
            mDoneButton.setOnTouchListener(this);
            mDoneButton.setEnabled(true);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                GameEngine.getInstance().playSound(getActivity(), "CARD_WHOOSH");
                SelectFragment fragment = (SelectFragment) getFragmentManager().findFragmentByTag("SelectFragment");
                fragment.updateFragment();
                getFragmentManager().popBackStack();
                break;
        }
        return true;
    }
}
