package com.example.culinarycompanion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecipeAdapter(
    private var recipes: List<Recipe>,
    private val onClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.tvRecipeName)
        private val cat  = itemView.findViewById<TextView>(R.id.tvCategory)
        fun bind(r: Recipe) {
            name.text = r.name
            cat.text  = r.category
            itemView.setOnClickListener { onClick(r) }
        }
    }

    override fun onCreateViewHolder(p: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(p.context).inflate(R.layout.item_recipe, p, false)
    )

    override fun getItemCount() = recipes.size

    override fun onBindViewHolder(h: ViewHolder, pos: Int) = h.bind(recipes[pos])

    /** Call this to refresh list from LiveData */
    fun setRecipes(newList: List<Recipe>) {
        recipes = newList
        notifyDataSetChanged()
    }
}