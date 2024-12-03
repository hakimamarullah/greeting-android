package com.example.artspace.model

import androidx.annotation.DrawableRes

data class ArtPhoto(
    val id: Int,
    val year: Int,
    val artist: String,
    val description: String?,
    @DrawableRes val painterResource: Int
)