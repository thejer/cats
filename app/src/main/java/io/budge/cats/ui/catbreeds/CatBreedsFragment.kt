package io.budge.cats.ui.catbreeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import io.budge.cats.App
import io.budge.cats.R
import io.budge.cats.data.utils.LoadingStatus
import io.budge.cats.databinding.FragmentCatBreedsBinding
import io.budge.cats.utils.EventObserver
import io.budge.cats.utils.showSnackbar
import javax.inject.Inject

class CatBreedsFragment : Fragment() {

    lateinit var binding: FragmentCatBreedsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: CatBreedsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatBreedsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().applicationContext as App).appComponent().inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CatBreedsViewModel::class.java)
        binding.viewModel = viewModel
        binding.catBreedsRecyclerview.adapter = CatBreedListAdapter{
            viewModel.openCatBreedDetails(it)
        }
        viewModel.loadingStatus.observe(viewLifecycleOwner, {
            it?.let {
                manageLoadingStates(it, view)
            }
        })

        viewModel.navigateToDetails.observe(viewLifecycleOwner, EventObserver{
            findNavController()
                .navigate(CatBreedsFragmentDirections.actionCatsBreedsFragmentToCatBreedDetailsFragment(it))
        })
    }

    private fun manageLoadingStates(loadingStatus: LoadingStatus, view: View) =
        when (loadingStatus) {
        is LoadingStatus.Loading -> binding.progressIndicator.show()
        is LoadingStatus.Success -> binding.progressIndicator.hide()
        is LoadingStatus.Error -> {
            binding.progressIndicator.hide()
            view.showSnackbar(
                loadingStatus.errorMessage,
                Snackbar.LENGTH_INDEFINITE,
                getString(R.string.retry)
            ){ viewModel.getCatBreeds() }
        }
    }

}