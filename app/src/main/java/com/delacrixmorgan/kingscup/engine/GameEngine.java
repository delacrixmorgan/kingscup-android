package com.delacrixmorgan.kingscup.engine;

import android.content.Context;
import android.support.annotation.NonNull;

import com.delacrixmorgan.kingscup.model.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 * Created by Delacrix Morgan on 09/10/2016.
 */

public class GameEngine {

    private static GameEngine sGameEngine;
    private ArrayList<Card> mDeck = new ArrayList<>();
    private int mKingCounter;


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

    private void buildDeck(Context context, String packageName) {
        int resourceSuit, resourceName, resourceAction;
        String stringSuit, stringName, stringAction;

        mKingCounter = 0;

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
        shuffleDeck();
    }

    public Card drawCard(int i) {
        Card card = mDeck.get(i);
        mDeck.remove(i);
        shuffleDeck();

        return card;
    }

    private void shuffleDeck() {
        Collections.shuffle(mDeck, new Random(System.nanoTime()));
    }

    public ArrayList<Card> getmDeck() {
        return mDeck;
    }

    public Card getCard(int i) {
        return mDeck.get(i);
    }
}
