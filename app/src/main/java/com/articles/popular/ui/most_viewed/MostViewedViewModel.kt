package com.articles.popular.ui.most_viewed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.articles.popular.model.ArticlesUseCase
import com.articles.popular.model.DataState
import com.articles.popular.model.MostViewsApiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MostViewedViewModel  @Inject constructor(
    val articlesUseCase: ArticlesUseCase
) : ViewModel() {

    private val _viewState = MutableLiveData<DataState<MostViewsApiData>>()
    val viewState: LiveData<DataState<MostViewsApiData>>
        get() = _viewState



    fun getMostViewed(section:String,period:Int) = viewModelScope.launch {

        articlesUseCase(section, period)
                .onEach { dataState ->
                    _viewState.value = dataState
                }.launchIn(viewModelScope)

    }
}