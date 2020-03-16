package com.delacrixmorgan.kingscup.menu.language

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.model.LanguageType
import kotlinx.android.synthetic.main.cell_language.view.*

class LanguageRecyclerViewAdapter(
    private val languageTypes: ArrayList<LanguageType>,
    private val listener: LanguageListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ViewType {
        Language,
        HelpTranslatePlaceholder
    }

    var selectedLanguage = LanguageType.English

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
                val isSelected = languageType == selectedLanguage
                holder.bind(isSelected, languageType)
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
        fun bind(isSelected: Boolean, languageType: LanguageType) = with(itemView) {
            updateViews(isSelected)

            flagTextView.text = languageType.flagEmoji
            descriptionTextView.text = languageType.authorNames
            titleTextView.text = languageType.name.toLowerCase().capitalize()

            setOnClickListener {
                listener.onLanguageSelected(adapterPosition, languageType)
            }
        }

        private fun updateViews(isSelected: Boolean) = with(itemView) {
            if (isSelected) {
                parentViewGroup.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorPrimary
                    )
                )
                titleTextView.setTextColor(
                    ContextCompat.getColor(
                        context,
                        android.R.color.white
                    )
                )
                descriptionTextView.setTextColor(
                    ContextCompat.getColor(
                        context,
                        android.R.color.white
                    )
                )
            } else {
                parentViewGroup.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorInactive
                    )
                )
                titleTextView.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorInactiveHint
                    )
                )
                descriptionTextView.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorInactiveHint
                    )
                )
            }
        }
    }

    class HelpTranslateViewHolder(itemView: View, private val listener: LanguageListener) :
        RecyclerView.ViewHolder(itemView) {
        fun bind() = with(itemView) {
            titleTextView.text = context.getString(
                R.string.fragment_menu_language_btn_help_translate
            )
            descriptionTextView.text = context.getString(
                R.string.msg_help_translate_description
            )
            setOnClickListener {
                listener.onHelpTranslateSelected(adapterPosition)
            }
        }
    }
}