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
    private val cellHeight: Int,
    private val cellWidth: Int,
    private val languageTypes: Array<LanguageType>,
    private val listener: LanguageListener
) : RecyclerView.Adapter<LanguageRecyclerViewAdapter.LanguageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.cell_card_language, parent, false
        )

        itemView.layoutParams.height = cellHeight
        itemView.layoutParams.width = cellWidth

        return LanguageViewHolder(itemView, this.listener)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val languageType = this.languageTypes[position]
        holder.updateDataSet(languageType)
    }

    override fun getItemCount() = this.languageTypes.size

    class LanguageViewHolder(itemView: View, listener: LanguageListener) :
        RecyclerView.ViewHolder(itemView) {
        private lateinit var languageType: LanguageType

        init {
            itemView.setOnClickListener {
                listener.onLanguageSelected(this.languageType)
            }
        }

        @SuppressLint("DefaultLocale")
        fun updateDataSet(languageType: LanguageType) {
            this.languageType = languageType

            this.itemView.languageTextView.text =
                languageType.name.toLowerCase(Locale.US).capitalize()
            this.itemView.authorTextView.text = languageType.authorNames
        }
    }
}