package com.example.culinarycompanion.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.culinarycompanion.R
import com.example.culinarycompanion.data.Recipe
import com.example.culinarycompanion.databinding.ItemRecipeBinding

class RecipeListAdapter(
    private val onClick: (Recipe) -> Unit,
    private val onFavoriteClick: (Recipe) -> Unit
) : ListAdapter<Recipe, RecipeListAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemRecipeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onClick,
            onFavoriteClick
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ViewHolder(
        private val b: ItemRecipeBinding,
        private val onClick: (Recipe) -> Unit,
        private val onFavoriteClick: (Recipe) -> Unit
    ) : RecyclerView.ViewHolder(b.root) {

        fun bind(recipe: Recipe) {
            b.recipe = recipe
            b.root.setOnClickListener { onClick(recipe) }
            b.btnFavorite.setOnClickListener {
                recipe.isFavorite = !recipe.isFavorite
                b.btnFavorite.setImageResource(
                    if (recipe.isFavorite) R.drawable.ic_favorite_filled
                    else R.drawable.ic_favorite_border
                )
                onFavoriteClick(recipe)
            }
            // reflect current state:
            b.btnFavorite.setImageResource(
                if (recipe.isFavorite) R.drawable.ic_favorite_filled
                else R.drawable.ic_favorite_border
            )
            b.executePendingBindings()
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(old: Recipe, new: Recipe) = old.id == new.id
            override fun areContentsTheSame(old: Recipe, new: Recipe) = old == new
        }
    }
}