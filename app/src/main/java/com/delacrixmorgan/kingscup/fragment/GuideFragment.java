package com.delacrixmorgan.kingscup.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.delacrixmorgan.kingscup.R;
import com.delacrixmorgan.kingscup.shared.Helper;

/**
 * Created by Delacrix Morgan on 04/03/2017.
 */

public class GuideFragment extends Fragment {
    private static String TAG = "GuideFragment";
    final Handler mHandler = new Handler();
    int counter = 0;
    private ImageView mSymbol;
    private TextView mGuide;
    private Button mSkipButton;
    private String mArrayGuide[] = {
            "Yes, this a drinking game. You can put all sorts of drinks and mix it together.\n\nDoesn't necessary has to be alcoholic.",
            "Pick a person to start the game, select a card and do what it says.\n\nThere is no escape!\nPass to the next person when you are done.",
            "When you draw a King from the deck, you will have the honour to choose any drinks of your liking into the King's Cup.\n\nPerson who draws the last King will have to finish the cup in one shot! Cheers mate."
    };

    private int mArraySymbol[] = {
            R.drawable.club_pink,
            R.drawable.diamond_pink,
            R.drawable.spade_pink
    };

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_guide, container, false);

        mSymbol = (ImageView) rootView.findViewById(R.id.iv_guide_symbol);
        mGuide = (TextView) rootView.findViewById(R.id.tv_guide);
        mSkipButton = (Button) rootView.findViewById(R.id.btn_skip);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (counter < 3) {
                    final Animation animation = new AlphaAnimation(1, 0);
                    animation.setDuration(1500);
                    animation.setInterpolator(new LinearInterpolator());
                    animation.setRepeatCount(Animation.INFINITE);
                    animation.setRepeatMode(Animation.REVERSE);

                    mSymbol.startAnimation(animation);

                    mSymbol.setBackgroundResource(mArraySymbol[counter]);
                    mGuide.setText(mArrayGuide[counter]);

                    mHandler.postDelayed(this, 3000);
                    counter++;
                } else {
                    mHandler.removeCallbacks(this);
                }

            }
        };

        mHandler.post(runnable);

        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacks(runnable);
                Helper.showFragment(getActivity(), new SelectFragment(), GuideFragment.TAG);
            }
        });
    }

}
