package com.articles.popular.model

import android.util.Log
import com.articles.popular.network.ArticlesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class ArticlesUseCase @Inject constructor(
    private val articlesApi: ArticlesApi
) {

    suspend operator fun invoke(section:String,period:Int): Flow<DataState<MostViewsApiData>> = flow {
        emit(DataState.Loading)
        try {
            val settings: MostViewsApiData = articlesApi.getMostViewedArticles(section,period)
            emit(DataState.Success(settings))
        } catch (e: Exception) {
            Log.i("Excep",e.toString())
            emit(DataState.Error(e))
        }
    }
}
