package com.delacrixmorgan.kingscup.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Card(val suitType: SuitType, val rank: String, val header: String, val body: String) :
    Parcelable