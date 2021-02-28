package io.budge.cats.di

import com.google.gson.Gson
import dagger.Lazy
import dagger.Module
import dagger.Provides
import io.budge.cats.BuildConfig
import io.budge.cats.data.api.CatsApiService
import io.budge.cats.utils.Constants.AUTH_KEY
import io.budge.cats.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class APIServiceModule {

    @Provides
    @Singleton
    fun provideDogsAPIService(
        client: Lazy<OkHttpClient>,
        gson: Gson
    ): CatsApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client.get())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CatsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGenericOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor {
                return@addInterceptor it.proceed(
                    it.request()
                        .newBuilder()
                        .addHeader("x-api-key", AUTH_KEY)
                        .build()
                )
            }.addInterceptor(interceptor)
            .build()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }


    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)
}
