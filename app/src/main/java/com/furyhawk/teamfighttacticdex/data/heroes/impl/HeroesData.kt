package com.furyhawk.teamfighttacticdex.data.heroes.impl

import android.content.res.Resources
import androidx.ui.graphics.imageFromResource
import com.furyhawk.teamfighttacticdex.R
import com.furyhawk.teamfighttacticdex.model.Hero

val post1 = Hero(
    id = "dc523f0ed25c",
    title = "A Little Thing about Android Module Paths",
    subtitle = "How to configure your module paths, instead of using Gradle’s default.",
    url = "https://medium.com/androiddevelopers/gradle-path-configuration-dc523f0ed25c",
    imageId = R.drawable.post_1,
    imageThumbId = R.drawable.post_1_thumb
)

val heroes: List<Hero> =
    listOf(
        post1,
        post1.copy(id = "post6"),
        post1.copy(id = "post7"),
        post1.copy(id = "post8")
    )

fun getPostsWithImagesLoaded(posts: List<Hero>, resources: Resources): List<Hero> {
    return posts.map {
        it.copy(
            image = imageFromResource(resources, it.imageId),
            imageThumb = imageFromResource(resources, it.imageThumbId)
        )
    }
}
