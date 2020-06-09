package com.furyhawk.teamfighttacticdex.model

import androidx.ui.graphics.ImageAsset

data class Hero (
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val url: String,
    val imageId: Int,
    val imageThumbId: Int,
    val image: ImageAsset? = null,
    val imageThumb: ImageAsset? = null
)