package io.budge.cats.ui.breeddetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.budge.cats.databinding.FragmentBreedDetailsBinding
import io.budge.cats.utils.hide
import io.budge.cats.utils.show
import io.budge.cats.utils.underline
import io.budge.cats.utils.viewUrl

class BreedDetailsFragment : Fragment() {

    private lateinit var binding: FragmentBreedDetailsBinding

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
        val args = BreedDetailsFragmentArgs.fromBundle(requireArguments())
        val catBreed = args.catBreed
        binding.catBreed = catBreed
        binding.readMore.underline()
        if (catBreed.wikipediaUrl.isBlank()) binding.readMore.hide()
        else binding.readMore.show()
        binding.readMore.setOnClickListener {
            if (catBreed.wikipediaUrl.isNotBlank())
                requireActivity().viewUrl(catBreed.wikipediaUrl)
        }

    }
}