package com.delacrixmorgan.kingscup.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.delacrixmorgan.kingscup.R;
import com.delacrixmorgan.kingscup.engine.GameEngine;

/**
 * Created by Delacrix Morgan on 17/03/2017.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private static String TAG = "CardAdapter";
    private Context mContext;
    private ProgressBar mProgressBar;

    public CardAdapter(Context context, ProgressBar progressBar) {
        mContext = context;
        mProgressBar = progressBar;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card, parent, false));
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, int position) {
        holder.selectCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setMax(mProgressBar.getMax() - 1);
                GameEngine.getInstance().playSound(mContext, "CARD_FLIP");
                GameEngine.getInstance().drawCard(mContext, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return GameEngine.getInstance().getmDeck().size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder {
        LinearLayout selectCard;

        private CardViewHolder(View itemView) {
            super(itemView);
            selectCard = itemView.findViewById(R.id.view_card_layout);
        }
    }
}
