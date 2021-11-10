package com.articles.popular.ui.most_viewed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MostViewedViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is most viewed Fragment"
    }
    val text: LiveData<String> = _text
}