package com.delacrixmorgan.kingscup.menu.language

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.model.LanguageType
import kotlinx.android.synthetic.main.cell_language.view.*

class LanguageRecyclerViewAdapter(
    private val languageTypes: Array<LanguageType>,
    private val listener: LanguageListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ViewType {
        Language,
        HelpTranslatePlaceholder
    }

    override fun getItemViewType(position: Int): Int {
        return if (position != itemCount - 1) {
            ViewType.Language.ordinal
        } else {
            ViewType.HelpTranslatePlaceholder.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ViewType.values()[viewType]) {
            ViewType.Language -> {
                LanguageViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.cell_language, parent, false
                    ), listener
                )
            }
            ViewType.HelpTranslatePlaceholder -> {
                HelpTranslateViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.cell_language_help_translate, parent, false
                    ), listener
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LanguageViewHolder -> {
                val languageType = languageTypes[position]
                holder.bind(languageType)
            }
            is HelpTranslateViewHolder -> {
                holder.bind()
            }
        }
    }

    override fun getItemCount() = languageTypes.size + 1

    class LanguageViewHolder(itemView: View, private val listener: LanguageListener) :
        RecyclerView.ViewHolder(itemView) {

        @SuppressLint("DefaultLocale")
        fun bind(languageType: LanguageType) = with(itemView) {
            flagTextView.text = languageType.flagEmoji
            authorTextView.text = languageType.authorNames
            languageTextView.text = languageType.name.toLowerCase().capitalize()

            setOnClickListener {
                listener.onLanguageSelected(adapterPosition, languageType)
            }
        }
    }

    class HelpTranslateViewHolder(itemView: View, private val listener: LanguageListener) :
        RecyclerView.ViewHolder(itemView) {
        fun bind() = with(itemView) {
            setOnClickListener {
                listener.onHelpTranslateSelected(adapterPosition)
            }
        }
    }
}