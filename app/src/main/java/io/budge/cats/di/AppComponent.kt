package io.budge.cats.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import io.budge.cats.ui.catbreeds.CatBreedsFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [APIServiceModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(target: CatBreedsFragment)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(app: Application): Builder
    }
}
