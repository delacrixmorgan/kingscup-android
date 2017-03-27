package com.delacrixmorgan.kingscup.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.delacrixmorgan.kingscup.R;
import com.delacrixmorgan.kingscup.adapter.CardAdapter;
import com.delacrixmorgan.kingscup.shared.Helper;

/**
 * Created by Delacrix Morgan on 04/03/2017.
 */

public class SelectFragment extends Fragment {
    private static String TAG = "SelectFragment";

    private RecyclerView mCardRecyclerView;
    private RecyclerView.LayoutManager mCardLayoutManager;
    private CardAdapter mCardAdapter;
    private Button mButtonNext;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select, container, false);

        mCardRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_select_card_recycler_view);
        mButtonNext = (Button) rootView.findViewById(R.id.button_next);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCardAdapter = new CardAdapter(getActivity());
        mCardLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        mCardRecyclerView.setLayoutManager(mCardLayoutManager);
        mCardRecyclerView.setAdapter(mCardAdapter);

        mCardRecyclerView.scrollToPosition(0);

        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.showFragment(getActivity(), new CardFragment(), SelectFragment.TAG);
            }
        });
    }
}
