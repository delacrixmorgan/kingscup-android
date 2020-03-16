package com.delacrixmorgan.kingscup.menu.language

import com.delacrixmorgan.kingscup.model.LanguageType

interface LanguageListener {
    fun onHelpTranslateSelected(position: Int)
    fun onLanguageSelected(position: Int, languageType: LanguageType)
}