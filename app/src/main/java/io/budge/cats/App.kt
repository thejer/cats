package io.budge.cats

import android.app.Application
import io.budge.cats.di.AppComponent
import io.budge.cats.di.DaggerAppComponent

class App: Application() {

    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    fun appComponent() = component
}