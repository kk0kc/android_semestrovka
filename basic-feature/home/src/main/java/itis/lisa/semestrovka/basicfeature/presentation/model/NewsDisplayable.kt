package itis.lisa.semestrovka.basicfeature.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsDisplayable(
    val id: String,
    val title: String,
    val description: String,
    val url: String,
    val author: String,
    val image: String,
    val language: String,
    val category: String,
    val published: String,
) : Parcelable

