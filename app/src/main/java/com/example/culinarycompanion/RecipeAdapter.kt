package com.example.culinarycompanion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.culinarycompanion.databinding.ItemRecipeBinding

data class Recipe(val id: Long, val name: String, val category: String)

class RecipeAdapter(
    private val onClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.VH>() {

    private val items = mutableListOf<Recipe>()

    fun submitList(list: List<Recipe>) {
        items.clear()
        items += list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemRecipeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    inner class VH(private val b: ItemRecipeBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(r: Recipe) {
            b.tvRecipeName.text = r.name
            b.tvCategory.text = r.category
            b.root.setOnClickListener { onClick(r) }
        }
    }
}