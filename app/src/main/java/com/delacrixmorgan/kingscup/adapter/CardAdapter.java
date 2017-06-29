package com.delacrixmorgan.kingscup.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.delacrixmorgan.kingscup.R;
import com.delacrixmorgan.kingscup.engine.GameEngine;

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

        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, int position) {
        holder.selectCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        ImageView selectCard;

        private CardViewHolder(View itemView) {
            super(itemView);
            selectCard = (ImageView) itemView.findViewById(R.id.select_card);
        }
    }
}
