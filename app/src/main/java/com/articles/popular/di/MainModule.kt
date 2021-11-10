package com.articles.popular.di

import com.articles.popular.BuildConfig
import com.articles.popular.network.ArticlesApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import okhttp3.Response






@InstallIn(SingletonComponent::class)
@Module
object MainModule {
    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }
    @Singleton
    @Provides
    fun provideRetrofit(gson:Gson): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor) //.addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .addNetworkInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request =
                        chain.request().newBuilder() // .addHeader(Constant.Header, authToken)
                            .build()
                    return chain.proceed(request)
                }

            }).build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(gson)
            )
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideOpenWeatherMapApi(retrofit: Retrofit): ArticlesApi {
        return retrofit.create(ArticlesApi::class.java)
    }
}