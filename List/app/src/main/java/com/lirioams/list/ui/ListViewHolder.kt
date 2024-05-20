package com.lirioams.list.ui

import androidx.recyclerview.widget.RecyclerView
import com.lirioams.list.R
import com.lirioams.list.data.remote.model.ListDto
import com.lirioams.list.databinding.ListElementBinding

class ListViewHolder(private val binding: ListElementBinding
): RecyclerView.ViewHolder(binding.root){

    val ivThumbnail = binding.ivThumbnail

    fun bind(element: ListDto){
        binding.tvName.text = element.name
        binding.tvAfiliacion.text = itemView.context.getString(
            R.string.affiliation,
            element.affiliation?: itemView.context.getString(R.string.none))
    }
}