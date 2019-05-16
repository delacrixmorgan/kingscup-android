package com.delacrixmorgan.kingscup.model

/**
 * LanguageType
 * kingscup-android
 *
 * Created by Delacrix Morgan on 25/03/2018.
 * Copyright (c) 2018 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

enum class LanguageType(var countryIso: String) {
    ENGLISH("en"),
    CHINESE("zh"),
    PORTUGUESE("pt"),
    DUTCH("nl"),
    SPANISH("es"),
    FINNISH("fi")
}