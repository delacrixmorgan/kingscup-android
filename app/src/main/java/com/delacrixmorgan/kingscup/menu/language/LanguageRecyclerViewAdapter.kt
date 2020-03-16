package com.delacrixmorgan.kingscup.menu.language

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.model.LanguageType
import kotlinx.android.synthetic.main.cell_card_language.view.*
import java.util.*

class LanguageRecyclerViewAdapter(
    private val languageTypes: Array<LanguageType>,
    private val listener: LanguageListener
) : RecyclerView.Adapter<LanguageRecyclerViewAdapter.LanguageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        return LanguageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cell_card_language, parent, false
            ), listener
        )
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val languageType = languageTypes[position]
        holder.updateDataSet(languageType)
    }

    override fun getItemCount() = languageTypes.size

    class LanguageViewHolder(itemView: View, private val listener: LanguageListener) :
        RecyclerView.ViewHolder(itemView) {

        @SuppressLint("DefaultLocale")
        fun updateDataSet(languageType: LanguageType) = with(itemView) {
            languageTextView.text = languageType.name.toLowerCase(
                Locale.US
            ).capitalize()

            flagTextView.text = languageType.flagEmoji

            authorTextView.text = languageType.authorNames

            setOnClickListener {
                listener.onLanguageSelected(languageType)
            }
        }
    }
}