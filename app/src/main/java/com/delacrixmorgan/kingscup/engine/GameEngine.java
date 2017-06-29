package com.delacrixmorgan.kingscup.engine;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.delacrixmorgan.kingscup.R;
import com.delacrixmorgan.kingscup.adapter.CardAdapter;
import com.delacrixmorgan.kingscup.fragment.CardFragment;
import com.delacrixmorgan.kingscup.model.Card;
import com.delacrixmorgan.kingscup.shared.Helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Delacrix Morgan on 09/10/2016.
 */

public class GameEngine {
    private static String TAG = "GameEngine";
    private static GameEngine sGameEngine;

    private ArrayList<Card> mDeck;
    private ArrayList<String> mGuideArray, mNextArray;
    private Boolean mCardSelected;
    private int mKingCounter, mCurrentCardPosition;
    private MediaPlayer[] mMediaPlayers;

    private GameEngine(@NonNull Context context) {
        mMediaPlayers = new MediaPlayer[2];
        mMediaPlayers[0] = MediaPlayer.create(context, R.raw.water_pour);
        mMediaPlayers[1] = MediaPlayer.create(context, R.raw.victory_cheer);

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

                if (context.getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).getBoolean(Helper.SOUND_EFFECTS_PREFERENCE, true)) {
                    if (mKingCounter < 1) {
                        PlayThread[] playThreads = new PlayThread[2];
                        for (int j = 0; j < 2; j++) {
                            playThreads[j] = new PlayThread();
                            playThreads[j].executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mMediaPlayers[j]);
                        }

                    } else {
                        mMediaPlayers[0].start();
                    }
                }
            }

            Helper.showAddFragmentSlideDown((Activity) context, new CardFragment(), "SelectFragment");
        }
    }

    public Boolean checkWin(CardAdapter adapter) {
        mDeck.remove(mCurrentCardPosition);
        adapter.notifyItemRemoved(mCurrentCardPosition);

        mCurrentCardPosition = 0;
        mCardSelected = false;

        return (mKingCounter < 1);
    }

    public void stopGame(CardAdapter adapter) {
        mDeck.clear();
        adapter.notifyDataSetChanged();
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

    public int getmKingCounter() {
        return mKingCounter;
    }

    public String getNextText() {
        Collections.shuffle(mNextArray, new Random(System.nanoTime()));
        return mNextArray.get(0);
    }

    public ArrayList<String> getmGuideArray() {
        return mGuideArray;
    }

    class PlayThread extends AsyncTask<MediaPlayer, Void, Void> {
        @Override
        protected Void doInBackground(MediaPlayer... player) {
            player[0].start();
            return null;
        }
    }
}
