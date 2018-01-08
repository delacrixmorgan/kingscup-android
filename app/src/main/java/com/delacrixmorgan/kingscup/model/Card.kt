package com.delacrixmorgan.kingscup.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Delacrix Morgan on 09/10/2016.
 **/

@SuppressLint("ParcelCreator")
@Parcelize
class Card(val suit: String, val rank: String, val header: String, val body: String) : Parcelable