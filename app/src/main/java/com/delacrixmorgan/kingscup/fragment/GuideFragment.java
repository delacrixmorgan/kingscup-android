package com.delacrixmorgan.kingscup.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.delacrixmorgan.kingscup.engine.GameEngine;
import com.delacrixmorgan.kingscup.shared.Helper;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Delacrix Morgan on 04/03/2017.
 */

public class GuideFragment extends Fragment {
    private static String TAG = "GuideFragment";

    private ImageView mSymbol;
    private Button mSkipButton;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_guide, container, false);

        mSymbol = (ImageView) rootView.findViewById(R.id.iv_guide_symbol);
        mSkipButton = (Button) rootView.findViewById(R.id.btn_skip);
        mViewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!getActivity().getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).getBoolean(Helper.QUICK_GUIDE_PREFERENCE, true)) {
            Helper.showFragment(getActivity(), new SelectFragment(), GuideFragment.TAG);
        }

        Helper.animateButtonGrow(getActivity(), mSkipButton);

        mViewPager.setAdapter(new sGuideAdapter(getActivity()));
        mTabLayout.setupWithViewPager(mViewPager, true);

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                final Animation animation = new AlphaAnimation(1, 0);
                animation.setDuration(2500);
                animation.setInterpolator(new LinearInterpolator());
                animation.setRepeatCount(Animation.INFINITE);
                animation.setRepeatMode(Animation.REVERSE);

                mSymbol.startAnimation(animation);
            }
        });

        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameEngine.getInstance().playSound(getActivity(), "BUTTON_CLICK");
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).edit();
                editor.putBoolean(Helper.QUICK_GUIDE_PREFERENCE, false);
                editor.apply();

                Helper.showFragment(getActivity(), new SelectFragment(), GuideFragment.TAG);
            }
        });
    }

    private class sGuideAdapter extends PagerAdapter {

        private Context mContext;
        private TextView mGuideText;

        sGuideAdapter(Context context) {
            mContext = context;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View rootView = inflater.inflate(R.layout.view_guide, collection, false);

            mGuideText = (TextView) rootView.findViewById(R.id.tv_guide);
            mGuideText.setText(GameEngine.getInstance().getmGuideArray().get(position));

            collection.addView(rootView);

            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((TextView) object);
        }

        @Override
        public int getCount() {
            return GameEngine.getInstance().getmGuideArray().size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


}
