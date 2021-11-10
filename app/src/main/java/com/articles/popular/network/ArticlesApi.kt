package com.articles.popular.network

import com.articles.popular.BuildConfig
import com.articles.popular.model.DataState
import com.articles.popular.model.MostViewsApiData
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticlesApi {

    @GET("mostpopular/v2/mostviewed/{section}/{period}.json")
    suspend fun getMostViewedArticles(
        @Path("section") section:String,
        @Path("period") period:Int,
        @Query("api-key") apikey:String= BuildConfig.API_KEY
    ): MostViewsApiData
}
