package com.delacrix.kingscup.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.delacrix.kingscup.R;
import com.delacrix.kingscup.shared.Helper;

/**
 * Created by delac on 04/03/2017.
 */

public class StartFragment extends Fragment {
    private static String TAG = "StartFragment";

    private Button mSkipButton;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_start, container, false);

        mSkipButton = (Button) rootView.findViewById(R.id.button_skip);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.showFragment(getActivity(), new SelectFragment(), StartFragment.TAG);
            }
        });
    }
}
