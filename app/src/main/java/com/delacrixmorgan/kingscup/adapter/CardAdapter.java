package com.delacrixmorgan.kingscup.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.delacrixmorgan.kingscup.R;
import com.delacrixmorgan.kingscup.fragment.CardFragment;
import com.delacrixmorgan.kingscup.shared.Helper;

/**
 * Created by Delacrix Morgan on 17/03/2017.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private static String TAG = "CardAdapter";

    private Context mContext;

    public CardAdapter(Context context) {
        mContext = context;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_card, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.showFragment((Activity) mContext, new CardFragment(), CardAdapter.TAG);
            }
        });

        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
//        return GameEngine.getInstance(mContext).getmDeck().size();
        return 52;
    }

    class CardViewHolder extends RecyclerView.ViewHolder {

        public CardViewHolder(View itemView) {
            super(itemView);
        }
    }
}
