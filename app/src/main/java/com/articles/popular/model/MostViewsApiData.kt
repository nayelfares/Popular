package com.articles.popular.model

import java.util.ArrayList

data class MostViewsApiData (
   val status : String,
   val results : ArrayList<Article>
   )