package com.delacrixmorgan.kingscup.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.delacrixmorgan.kingscup.R;
import com.delacrixmorgan.kingscup.engine.GameEngine;
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

        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, final int position) {
        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!GameEngine.getInstance().mCardSelected){
                    GameEngine.getInstance().mCardSelected = true;
                    GameEngine.getInstance().drawCard(position);

                    Bundle bundle = new Bundle();
                    bundle.putInt("position", position);

                    Fragment cardFragment = new CardFragment();
                    cardFragment.setArguments(bundle);

                    notifyItemRemoved(position);

                    Helper.showFragmentSlideDown((Activity) mContext, cardFragment, CardAdapter.TAG);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return GameEngine.getInstance().getmDeck().size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder {
        FrameLayout frameLayout;

        private CardViewHolder(View itemView) {
            super(itemView);
            frameLayout = (FrameLayout) itemView.findViewById(R.id.frame_layout_card);
        }
    }
}
