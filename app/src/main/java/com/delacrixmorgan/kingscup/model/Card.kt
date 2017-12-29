package com.delacrixmorgan.kingscup.model

import java.io.Serializable

/**
 * Created by Delacrix Morgan on 09/10/2016.
 **/

data class Card(val suit: String, val rank: String, val header: String, val body: String) : Serializable