package com.delacrixmorgan.kingscup.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private TextView mGuide;
    private Button mSkipButton;
    private String mArrayGuide[] = {
            "Yes, this a drinking game. You can put all sorts of drinks and mix it together. Doesn't necessary has to be alcoholic.",
            "Pick a person to start the game, select a card and do what it says. There is no escape! Pass to the next person when you are done.",
            "When you draw a King from the deck, you will have the honour to choose any drinks of your liking into the King's Cup. Person who draws the last King will have to finish the cup in one shot! Cheers mate."
    };

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_start, container, false);

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
                    mGuide.setText(mArrayGuide[counter]);
                    mHandler.postDelayed(this, 1000);
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
