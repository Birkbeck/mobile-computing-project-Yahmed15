package com.example.culinarycompanion.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.culinarycompanion.data.Recipe
import com.example.culinarycompanion.databinding.ItemFavoriteRecipeBinding

class FavoriteRecipesAdapter(
    private val onClick: (Recipe) -> Unit,
    private val onFavoriteClick: (Recipe) -> Unit
) : ListAdapter<Recipe, FavoriteRecipesAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoriteRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, onClick, onFavoriteClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemFavoriteRecipeBinding,
        private val onClick: (Recipe) -> Unit,
        private val onFavoriteClick: (Recipe) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var currentRecipe: Recipe? = null

        init {
            // tapping the card → detail
            binding.root.setOnClickListener {
                currentRecipe?.let(onClick)
            }
            // tapping the heart → toggle favorite
            binding.btnFavoriteDetail.setOnClickListener {
                currentRecipe?.let(onFavoriteClick)
            }
        }

        fun bind(recipe: Recipe) {
            currentRecipe = recipe
            binding.recipe = recipe
            // no need to manually set icon here, your XML has a static filled-heart
            binding.executePendingBindings()
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) =
                oldItem == newItem
        }
    }
}