package com.delacrixmorgan.kingscup.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.delacrixmorgan.kingscup.R;
import com.delacrixmorgan.kingscup.engine.GameEngine;
import com.delacrixmorgan.kingscup.model.Card;

/**
 * Created by Delacrix Morgan on 09/10/2016.
 */

public class CardFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {

    private static String TAG = "CardFragment";

    private TextView mTextName, mTextAction, mTextLightValue, mTextDarkValue;
    private ImageView mLightLargeSymbol, mLightSmallSymbol, mDarkSmallSymbol;
    private Button mNextButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_card, container, false);
        rootView.setOnTouchListener(this);

        mTextName = (TextView) rootView.findViewById(R.id.card_name);
        mTextAction = (TextView) rootView.findViewById(R.id.card_action);
        mTextLightValue = (TextView) rootView.findViewById(R.id.card_light_value);
        mTextDarkValue = (TextView) rootView.findViewById(R.id.card_dark_value);

        mLightLargeSymbol = (ImageView) rootView.findViewById(R.id.card_light_lg_symbol);
        mLightSmallSymbol = (ImageView) rootView.findViewById(R.id.card_light_sm_symbol);
        mDarkSmallSymbol = (ImageView) rootView.findViewById(R.id.card_dark_sm_symbol);

        mNextButton = (Button) rootView.findViewById(R.id.button_next);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Card card = GameEngine.getInstance().getCurrentCard();

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

        mNextButton.setText(GameEngine.getInstance().getNextText());
        mNextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_next:
                getActivity().getFragmentManager().findFragmentByTag("SelectFragment").onResume();
                getActivity().getFragmentManager().popBackStack();

                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Animation animationScaleDown = AnimationUtils.loadAnimation(getActivity(), R.anim.pop_in);

                AnimationSet animShrink = new AnimationSet(true);
                animShrink.addAnimation(animationScaleDown);
                mNextButton.startAnimation(animShrink);

                return true;

            case MotionEvent.ACTION_UP:
                Animation animationScaleUp = AnimationUtils.loadAnimation(getActivity(), R.anim.pop_out);

                AnimationSet animGrow = new AnimationSet(true);
                animGrow.addAnimation(animationScaleUp);
                mNextButton.startAnimation(animGrow);

                return true;

            default:
                break;
        }
        return false;
    }
}
