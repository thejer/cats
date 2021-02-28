package io.budge.cats.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.budge.cats.ui.catbreeds.CatBreedsViewModel

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CatBreedsViewModel::class)
    abstract fun bindMainViewModel(viewModel: CatBreedsViewModel): ViewModel
}
