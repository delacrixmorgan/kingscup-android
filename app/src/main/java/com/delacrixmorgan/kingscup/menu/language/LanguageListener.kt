package com.delacrixmorgan.kingscup.menu.language

import com.delacrixmorgan.kingscup.model.LanguageType

interface LanguageListener {
    fun onLanguageSelected(languageType: LanguageType)
}