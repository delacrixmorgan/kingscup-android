package com.delacrixmorgan.kingscup.model;

/**
 * Created by Delacrix Morgan on 09/10/2016.
 */

public class Card {
    private String mValue, mSuit, mName, mAction;

    public Card(String mValue, String mSuit, String mName, String mAction) {
        this.mValue = mValue;
        this.mSuit = mSuit;
        this.mName = mName;
        this.mAction = mAction;
    }

    public String getmValue() {
        return mValue;
    }
    
    public String getmSuit() {
        return mSuit;
    }

    public String getmName() {
        return mName;
    }

    public String getmAction() {
        return mAction;
    }

}
