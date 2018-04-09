package com.delacrixmorgan.kingscup.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Card
 * kingscup-android
 *
 * Created by Delacrix Morgan on 25/03/2018.
 * Copyright (c) 2018 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

@SuppressLint("ParcelCreator")
@Parcelize
class Card(val suit: String, val rank: String, val header: String, val body: String) : Parcelable