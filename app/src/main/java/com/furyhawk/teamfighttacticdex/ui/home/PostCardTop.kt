/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.furyhawk.teamfighttacticdex.ui.home

import androidx.compose.Composable
import androidx.ui.core.ContentScale
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.clip
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.layout.Spacer
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.layout.preferredHeight
import androidx.ui.layout.preferredHeightIn
import androidx.ui.material.EmphasisAmbient
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ProvideEmphasis
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.furyhawk.teamfighttacticdex.data.heroes.impl.getPostsWithImagesLoaded
import com.furyhawk.teamfighttacticdex.data.heroes.impl.heroes
import com.furyhawk.teamfighttacticdex.data.heroes.impl.hero1
import com.furyhawk.teamfighttacticdex.model.Hero
import com.furyhawk.teamfighttacticdex.ui.ThemedPreview

@Composable
fun PostCardTop(post: Hero) {
    // TUTORIAL CONTENT STARTS HERE
    val typography = MaterialTheme.typography
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        post.image?.let { image ->
            val imageModifier = Modifier
                .preferredHeightIn(minHeight = 180.dp)
                .fillMaxWidth()
                .clip(shape = MaterialTheme.shapes.medium)
            Image(image, modifier = imageModifier, contentScale = ContentScale.Crop)
        }
        Spacer(Modifier.preferredHeight(16.dp))

        val emphasisLevels = EmphasisAmbient.current
        ProvideEmphasis(emphasisLevels.high) {
            Text(
                text = post.title,
                style = typography.h6
            )
//            Text(
//                text = post.metadata.author.name,
//                style = typography.body2
//            )
        }
//        ProvideEmphasis(emphasisLevels.medium) {
//            Text(
//                text = "${post.metadata.date} - ${post.metadata.readTimeMinutes} min read",
//                style = typography.body2
//            )
//        }
    }
}
// TUTORIAL CONTENT ENDS HERE

// Preview section

@Preview("Default colors")
@Composable
fun TutorialPreview() {
    TutorialPreviewTemplate()
}

@Preview("Dark theme")
@Composable
fun TutorialPreviewDark() {
    TutorialPreviewTemplate(darkTheme = true)
}

@Preview("Font scaling 1.5", fontScale = 1.5f)
@Composable
fun TutorialPreviewFontscale() {
    TutorialPreviewTemplate()
}

@Composable
fun TutorialPreviewTemplate(
    darkTheme: Boolean = false
) {
    val context = ContextAmbient.current
    val previewPosts = getPostsWithImagesLoaded(heroes.subList(1, 2), context.resources)
    val post = previewPosts[0]

    ThemedPreview(darkTheme) {
        PostCardTop(post)
    }
}

@Preview("Post card top")
@Composable
fun PreviewPostCardTop() {
    ThemedPreview {
        PostCardTop(post = hero1)
    }
}

@Preview("Post card top dark theme")
@Composable
fun PreviewPostCardTopDark() {
    ThemedPreview(darkTheme = true) {
        PostCardTop(post = hero1)
    }
}
