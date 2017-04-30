package com.delacrixmorgan.kingscup.engine;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.delacrixmorgan.kingscup.adapter.CardAdapter;
import com.delacrixmorgan.kingscup.fragment.CardFragment;
import com.delacrixmorgan.kingscup.model.Card;
import com.delacrixmorgan.kingscup.shared.Helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 * Created by Delacrix Morgan on 09/10/2016.
 */

public class GameEngine {

    private static GameEngine sGameEngine;

    private CardAdapter mCardAdapter;
    private Boolean mCardSelected;
    private ArrayList<Card> mDeck = new ArrayList<>();
    private int mKingCounter;
    private int mCurrentCardPosition;


    private GameEngine(@NonNull Context context) {
        buildDeck(context, context.getPackageName());
    }

    public static synchronized GameEngine newInstance(@NonNull Context context) {
        sGameEngine = new GameEngine(context);

        return sGameEngine;
    }

    public static synchronized GameEngine getInstance() {
        return sGameEngine;
    }

    public void initRecyclerView(Context context, RecyclerView recyclerView) {
        mCardAdapter = new CardAdapter(context);

        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(mCardAdapter);
        recyclerView.scrollToPosition(0);
    }

    private void buildDeck(Context context, String packageName) {
        int resourceSuit, resourceName, resourceAction;
        String stringValue, stringSuit, stringName, stringAction;

        mCardSelected = false;
        mKingCounter = 0;

        for (int i = 1; i <= 4; i++) {
            resourceSuit = context.getResources().getIdentifier("suit_" + i, "string", packageName);
            stringSuit = context.getString(resourceSuit);

            for (int j = 1; j <= 13; j++) {
                resourceName = context.getResources().getIdentifier("name_" + j, "string", packageName);
                resourceAction = context.getResources().getIdentifier("action_" + j, "string", packageName);

                stringName = context.getString(resourceName);
                stringAction = context.getString(resourceAction);

                switch (j) {
                    case 1:
                        stringValue = "A";
                        break;

                    case 11:
                        stringValue = "J";
                        break;

                    case 12:
                        stringValue = "Q";
                        break;

                    case 13:
                        stringValue = "K";
                        break;

                    default:
                        stringValue = String.valueOf(j);
                }

                mDeck.add(new Card(stringValue, stringSuit, stringName, stringAction));
            }
        }

        shuffleDeck();
    }

    public void drawCard(Context context, int i) {
        if (!mCardSelected) {
            mCurrentCardPosition = i;
            mCardSelected = true;
            Helper.showFragmentSlideDown((Activity) context, new CardFragment(), mCardAdapter.getClass().getSimpleName());
        }
    }

    public void popCard() {
        mDeck.remove(mCurrentCardPosition);
        mCardAdapter.notifyItemRemoved(mCurrentCardPosition);

        mCurrentCardPosition = 0;
        mCardSelected = false;
    }

    public Card getCurrentCard() {
        return mDeck.get(mCurrentCardPosition);
    }

    private void shuffleDeck() {
        Collections.shuffle(mDeck, new Random(System.nanoTime()));
    }

    public ArrayList<Card> getmDeck() {
        return mDeck;
    }
}
