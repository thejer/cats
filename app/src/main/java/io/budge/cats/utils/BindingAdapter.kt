package io.budge.cats.utils

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import io.budge.cats.R
import io.budge.cats.data.model.CatBreed
import io.budge.cats.ui.catbreeds.CatBreedListAdapter


@BindingAdapter("imageUrl")
fun bindImageView(imageView: ImageView, url: String?) {
    imageView.load(url) {
        placeholder(R.drawable.ic_pet)
        error(R.drawable.ic_pet)
        crossfade(true)
    }
}


@BindingAdapter("catBreedsList")
fun bindCats(recyclerView: RecyclerView, data: MutableList<CatBreed>?) =
    if (data.isNullOrEmpty())
        recyclerView.hide()
    else {
        recyclerView.show()
        val adapter = recyclerView.adapter as CatBreedListAdapter
        adapter.submitList(data)
    }

@BindingAdapter("catBreedsList")
fun bindEmptyNotesView(emptyCatBreedsView: ConstraintLayout, data: MutableList<CatBreed>?) =
    if (data.isNullOrEmpty()) emptyCatBreedsView.show()
    else emptyCatBreedsView.hide()