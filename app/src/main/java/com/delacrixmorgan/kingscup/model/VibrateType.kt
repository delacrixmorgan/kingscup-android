package com.delacrixmorgan.kingscup.model

/**
 * VibrateType
 * kingscup-android
 *
 * Created by Delacrix Morgan on 25/03/2018.
 * Copyright (c) 2018 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

enum class VibrateType(var duration: Long) {
    SHORT(250L),
    LONG(2000L);
}