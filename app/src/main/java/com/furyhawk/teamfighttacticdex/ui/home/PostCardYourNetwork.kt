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
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.layout.*
import androidx.ui.material.Card
import androidx.ui.material.EmphasisAmbient
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ProvideEmphasis
import androidx.ui.material.ripple.ripple
import androidx.ui.res.imageResource
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.furyhawk.teamfighttacticdex.R
import com.furyhawk.teamfighttacticdex.data.heroes.impl.hero1
import com.furyhawk.teamfighttacticdex.model.Hero
import com.furyhawk.teamfighttacticdex.model.PostAuthor
import com.furyhawk.teamfighttacticdex.ui.Screen
import com.furyhawk.teamfighttacticdex.ui.ThemedPreview
import com.furyhawk.teamfighttacticdex.ui.navigateTo

@Composable
fun PostCardPopular(post: Hero, modifier: Modifier = Modifier) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.preferredSize(280.dp, 240.dp)) {
        Box(Modifier.ripple().clickable(onClick = { navigateTo(Screen.Article(post.id)) }
        ), children = {
            Column {
                val image = post.image ?: imageResource(R.drawable.placeholder_4_3)
                Image(
                    asset = image,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .preferredHeight(100.dp)
                        .fillMaxSize())

                Column(modifier = Modifier.padding(16.dp)) {
                    val emphasisLevels = EmphasisAmbient.current
                    ProvideEmphasis(emphasisLevels.high) {
                        Text(
                            text = post.title,
                            style = MaterialTheme.typography.h6,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = post.metadata.author.name,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.body2
                        )
                    }
                    ProvideEmphasis(emphasisLevels.high) {
                        Text(
                            text = "${post.metadata.date} - " +
                                    "${post.metadata.readTimeMinutes} min read",
                            style = MaterialTheme.typography.body2
                        )
                    }
                }
            }
        })
    }
}

@Preview("Regular colors")
@Composable
fun PreviewPostCardPopular() {
    ThemedPreview {
        PostCardPopular(post = hero1)
    }
}

@Preview("Dark colors")
@Composable
fun PreviewPostCardPopularDark() {
    ThemedPreview(darkTheme = true) {
        PostCardPopular(post = hero1)
    }
}

@Preview("Regular colors, long text")
@Composable
fun PreviewPostCardPopularLongText() {
    val loremIpsum = """
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras ullamcorper pharetra massa,
        sed suscipit nunc mollis in. Sed tincidunt orci lacus, vel ullamcorper nibh congue quis.
        Etiam imperdiet facilisis ligula id facilisis. Suspendisse potenti. Cras vehicula neque sed
        nulla auctor scelerisque. Vestibulum at congue risus, vel aliquet eros. In arcu mauris,
        facilisis eget magna quis, rhoncus volutpat mi. Phasellus vel sollicitudin quam, eu
        consectetur dolor. Proin lobortis venenatis sem, in vestibulum est. Duis ac nibh interdum,
    """.trimIndent()
    ThemedPreview {
        PostCardPopular(
            post = hero1.copy(
                title = "Title$loremIpsum",
                metadata = hero1.metadata.copy(
                    author = PostAuthor("Author: $loremIpsum"),
                    readTimeMinutes = Int.MAX_VALUE
                )
            )
        )
    }
}
