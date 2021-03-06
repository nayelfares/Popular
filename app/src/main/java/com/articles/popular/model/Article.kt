package com.articles.popular.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article (
    val uri            : String,
    val url            : String,
    val id             : Long,
    val source         : String,
    val published_date : String,
    val updated        : String,
    val section        : String,
    val subsection     : String,
    val nytdsection    : String,
    val adx_keywords   : String,
    val byline         : String,
    val title          : String,
    val abstract       : String
 ):Parcelable