package com.lirioams.list.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lirioams.list.R

import com.lirioams.list.data.remote.model.ListDto
import com.lirioams.list.databinding.ListElementBinding


class ListAdapter(
    private val elements: List<ListDto>,
    private val onElementClicked: (ListDto) -> Unit
): RecyclerView.Adapter<ListViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ListElementBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = elements.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val element = elements.getOrNull(position)
        if (element != null) {
            holder.bind(element)

            // Usando Glide para cargar la imagen del avatar
            Glide.with(holder.itemView.context)
                .load(element.image)
                .placeholder(R.drawable.loading_anim)
                .error(R.drawable.noprofile)
                .into(holder.ivThumbnail)

            holder.itemView.setOnClickListener {
                onElementClicked(element)
            }
        } else {
            // Manejo de posición inválida
            Log.e("ListAdapter", holder.itemView.context.getString(R.string.invalid_position, position))
        }
    }
}