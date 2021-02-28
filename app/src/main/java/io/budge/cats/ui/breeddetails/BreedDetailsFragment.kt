package io.budge.cats.ui.breeddetails

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import io.budge.cats.databinding.FragmentBreedDetailsBinding
import io.budge.cats.ui.MainActivity
import io.budge.cats.utils.hide
import io.budge.cats.utils.show
import io.budge.cats.utils.underline
import io.budge.cats.utils.viewUrl

class BreedDetailsFragment : Fragment() {

    private lateinit var binding: FragmentBreedDetailsBinding
    private val mainActivity: MainActivity
        get() {
            return activity as? MainActivity ?: throw IllegalStateException("Not attached!")
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreedDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.setStatusBarColor(Color.TRANSPARENT)

        val args: BreedDetailsFragmentArgs by navArgs()
        val catBreed = args.catBreed

        binding.catBreed = catBreed
        binding.readMore.underline()

        if (catBreed.wikipediaUrl.isNullOrBlank()) binding.readMore.hide()
        else binding.readMore.show()

        binding.readMore.setOnClickListener {
            if (!catBreed.wikipediaUrl.isNullOrBlank())
                requireActivity().viewUrl(catBreed.wikipediaUrl)
        }

    }
}