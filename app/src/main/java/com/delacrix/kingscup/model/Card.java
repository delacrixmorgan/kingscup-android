package com.delacrix.kingscup.model;

/**
 * Created by Delacrix on 09/10/2016.
 */

public class Card {
    private String mSuit, mName, mAction;

    public Card(String mSuit, String mName, String mAction) {
        this.mSuit = mSuit;
        this.mName = mName;
        this.mAction = mAction;
    }

    public String getmSuit() {
        return mSuit;
    }

    public void setmSuit(String mSuit) {
        this.mSuit = mSuit;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmAction() {
        return mAction;
    }

    public void setmAction(String mAction) {
        this.mAction = mAction;
    }
}
