package com.delacrixmorgan.kingscup.menu.language

import com.delacrixmorgan.kingscup.model.LanguageType

/**
 * LanguageListener
 * kingscup-android
 *
 * Created by Delacrix Morgan on 16/05/2019.
 * Copyright (c) 2019 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

interface LanguageListener {
    fun onLanguageSelected(languageType: LanguageType)
}