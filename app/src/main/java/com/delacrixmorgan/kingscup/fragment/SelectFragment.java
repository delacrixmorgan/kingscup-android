package com.delacrixmorgan.kingscup.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.delacrixmorgan.kingscup.R;
import com.delacrixmorgan.kingscup.adapter.CardAdapter;

/**
 * Created by Delacrix Morgan on 04/03/2017.
 */

public class SelectFragment extends Fragment {

    private RecyclerView mCardRecyclerView;
    private RecyclerView.LayoutManager mCardLayoutManager;
    private CardAdapter mCardAdapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select, container, false);

        mCardRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_select_card_recycler_view);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCardAdapter = new CardAdapter(getActivity());
        mCardLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);

        mCardRecyclerView.setLayoutManager(mCardLayoutManager);
        mCardRecyclerView.setAdapter(mCardAdapter);

        mCardRecyclerView.scrollToPosition(0);
    }
}
