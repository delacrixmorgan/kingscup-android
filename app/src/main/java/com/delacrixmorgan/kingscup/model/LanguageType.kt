package com.delacrixmorgan.kingscup.model

/**
 * LanguageType
 * kingscup-android
 *
 * Created by Delacrix Morgan on 25/03/2018.
 * Copyright (c) 2018 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

enum class LanguageType(val countryIso: String, val authorNames: String) {
    ENGLISH("en", "Delacrix Morgan"),
    CHINESE("zh", "Yuki Sora"),
    PORTUGUESE("pt", "Lays Correia"),
    DUTCH("nl", "Kasper Nooteboom"),
    SPANISH("es", "Santos Martinez\nGonzo Fernandez"),
    FINNISH("fi", "Karim Moubarik"),
    FRENCH("fr", "David Chitchong Thingee"),
    GERMAN("de", "Roland Stuhler"),
    CZECH("cs", "Michal Matlach"),
    HUNGARIAN("hu", "Dávid Kardos"),
    FILIPINO("fil", "Rexson Bernal"),
    JAPANESE("ja", "Yukiko Kimura")
}