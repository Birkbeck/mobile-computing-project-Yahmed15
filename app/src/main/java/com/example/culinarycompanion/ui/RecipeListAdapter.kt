package com.example.culinarycompanion.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.culinarycompanion.data.Recipe
import com.example.culinarycompanion.databinding.ItemRecipeBinding

class RecipeListAdapter(
    private val onClick: (Recipe) -> Unit
) : ListAdapter<Recipe, RecipeListAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemRecipeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(private val b: ItemRecipeBinding) :
        RecyclerView.ViewHolder(b.root) {

        fun bind(r: Recipe) {
            b.tvRecipeName.text = r.name
            b.tvCategory  .text = r.category
            b.root.setOnClickListener { onClick(r) }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(old: Recipe, new: Recipe) = old.id == new.id
            override fun areContentsTheSame(old: Recipe, new: Recipe) = old == new
        }
    }
}