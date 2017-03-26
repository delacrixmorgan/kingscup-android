package com.delacrixmorgan.kingscup.engine;

import android.content.Context;
import android.support.annotation.NonNull;

import com.delacrixmorgan.kingscup.model.Card;

import java.util.ArrayList;


/**
 * Created by Delacrix Morgan on 09/10/2016.
 */

public class GameEngine {
    private static GameEngine sGameEngine;

    private ArrayList<Card> mDeck = new ArrayList<>();

    public static synchronized GameEngine getInstance(@NonNull Context context) {
        if (sGameEngine == null) {
            sGameEngine = new GameEngine(context);
        }

        return sGameEngine;
    }

    private GameEngine(@NonNull Context context) {
        buildDeck(context, context.getPackageName());
    }

    private void buildDeck(Context context, String packageName) {
        int resourceSuit, resourceName, resourceAction;
        String stringSuit, stringName, stringAction;

        for (int i = 1; i <= 4; i++) {
            resourceSuit = context.getResources().getIdentifier("suit_" + i, "string", packageName);
            stringSuit = context.getString(resourceSuit);

            for (int j = 1; j <= 13; j++) {
                resourceName = context.getResources().getIdentifier("name_" + j, "string", packageName);
                resourceAction = context.getResources().getIdentifier("action_" + j, "string", packageName);

                stringName = context.getString(resourceName);
                stringAction = context.getString(resourceAction);

                mDeck.add(new Card(stringSuit, stringName, stringAction));
            }
        }
    }

    public ArrayList<Card> getmDeck() {
        return mDeck;
    }

    public void setmDeck(ArrayList<Card> mDeck) {
        this.mDeck = mDeck;
    }

    public Card drawCard() {
        Card card = mDeck.get(0);
        mDeck.remove(0);

        return card;
    }
}
