package com.delacrixmorgan.kingscup.model;

/**
 * Created by Delacrix Morgan on 09/10/2016.
 */

public class Card {
    private String mValue,mSuit, mName, mAction;

    public Card(String mValue, String mSuit, String mName, String mAction) {
        this.mValue = mValue;
        this.mSuit = mSuit;
        this.mName = mName;
        this.mAction = mAction;
    }

    public String getmValue() {
        return mValue;
    }

    public void setmValue(String mValue) {
        this.mValue = mValue;
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
