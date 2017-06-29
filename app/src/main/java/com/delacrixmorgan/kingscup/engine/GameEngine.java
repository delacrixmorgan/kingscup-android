package com.delacrixmorgan.kingscup.engine;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;

import com.delacrixmorgan.kingscup.R;
import com.delacrixmorgan.kingscup.adapter.CardAdapter;
import com.delacrixmorgan.kingscup.fragment.CardFragment;
import com.delacrixmorgan.kingscup.model.Card;
import com.delacrixmorgan.kingscup.shared.Helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Delacrix Morgan on 09/10/2016.
 */

public class GameEngine {
    private static String TAG = "GameEngine";
    private static GameEngine sGameEngine;

    private int mKingCounter, mCurrentCardPosition;
    private Boolean mCardSelected;
    private ArrayList<Card> mDeck;
    private ArrayList<String> mGuideArray, mNextArray;
    private HashMap<String, MediaPlayer> mMediaPlayers;

    private GameEngine(@NonNull Context context) {
        mMediaPlayers = new HashMap<>();
        mMediaPlayers.put("KING", MediaPlayer.create(context, R.raw.king));
        mMediaPlayers.put("BACK", MediaPlayer.create(context, R.raw.back));
        mMediaPlayers.put("GAME_OVER", MediaPlayer.create(context, R.raw.game_over));
        mMediaPlayers.put("CARD_FLIP", MediaPlayer.create(context, R.raw.card_flip));
        mMediaPlayers.put("CARD_WHOOSH", MediaPlayer.create(context, R.raw.card_whoosh));
        mMediaPlayers.put("CARD_SHUFFLE", MediaPlayer.create(context, R.raw.card_shuffle));
        mMediaPlayers.put("BUTTON_CLICK", MediaPlayer.create(context, R.raw.button_click));

        buildDeck(context, context.getPackageName());
        buildArray(context, context.getPackageName());
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
        String stringValue, stringSuit, stringName, stringAction;

        mKingCounter = 4;
        mCardSelected = false;
        mDeck = new ArrayList<>();

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

    private void buildArray(Context context, String packageName) {
        mGuideArray = new ArrayList<>();
        mNextArray = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            mGuideArray.add(context.getString(context.getResources().getIdentifier("guide_" + i, "string", packageName)));
        }

        for (int j = 1; j <= 10; j++) {
            mNextArray.add(context.getString(context.getResources().getIdentifier("next_" + j, "string", packageName)));
        }
    }

    public void drawCard(Context context, int i) {
        if (!mCardSelected) {
            mCurrentCardPosition = i;
            mCardSelected = true;

            if (mDeck.get(i).getmName().equals("King")) {
                mKingCounter--;
                playSound(context, "KING");
            }

            Helper.showAddFragmentSlideDown((Activity) context, new CardFragment(), "SelectFragment");
        }
    }

    public void playSound(Context context, String key){
        if (context.getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).getBoolean(Helper.SOUND_EFFECTS_PREFERENCE, true)){
            mMediaPlayers.get(key).start();
        }
    }

    public void updateCardAdapter(CardAdapter adapter) {
        mDeck.remove(mCurrentCardPosition);
        adapter.notifyItemRemoved(mCurrentCardPosition);

        mCurrentCardPosition = 0;
        mCardSelected = false;

        if (mKingCounter <  1){
            mDeck.clear();
            adapter.notifyDataSetChanged();
        }
    }

    public String getNextText() {
        Collections.shuffle(mNextArray, new Random(System.nanoTime()));
        return mNextArray.get(0);
    }

    public Card getCurrentCard() {
        return mDeck.get(mCurrentCardPosition);
    }

    private void shuffleDeck() {
        Collections.shuffle(mDeck, new Random(System.nanoTime()));
    }

    public int getmKingCounter() {
        return mKingCounter;
    }

    public ArrayList<Card> getmDeck() {
        return mDeck;
    }

    public ArrayList<String> getmGuideArray() {
        return mGuideArray;
    }
}
