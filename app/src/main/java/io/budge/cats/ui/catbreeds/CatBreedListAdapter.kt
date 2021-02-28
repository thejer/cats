package io.budge.cats.ui.catbreeds

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.budge.cats.R
import io.budge.cats.data.model.CatBreed
import io.budge.cats.databinding.CatItemLayoutBinding
import io.budge.cats.utils.inflate

class CatBreedListAdapter(
    val catBreedClickListener: (CatBreed) -> Unit,
    ): ListAdapter<CatBreed, CatBreedListAdapter.CatBreedsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatBreedsViewHolder =
        CatBreedsViewHolder(CatItemLayoutBinding.bind(parent.inflate(R.layout.cat_item_layout)))


    override fun onBindViewHolder(holder: CatBreedsViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object  DiffCallback : DiffUtil.ItemCallback<CatBreed>() {

        override fun areItemsTheSame(oldItem: CatBreed, newItem: CatBreed): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CatBreed, newItem: CatBreed): Boolean =
            oldItem == newItem
    }

    inner class CatBreedsViewHolder (private val binding: CatItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CatBreed) {
            binding.catBreed = item
            binding.catBreedCard.setOnClickListener { catBreedClickListener(item) }
            binding.executePendingBindings()
        }
    }
}