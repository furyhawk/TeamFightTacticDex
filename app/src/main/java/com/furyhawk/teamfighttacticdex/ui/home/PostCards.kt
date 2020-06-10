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
import androidx.ui.core.Modifier
import androidx.ui.core.clip
import androidx.ui.foundation.*
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.padding
import androidx.ui.layout.preferredSize
import androidx.ui.material.EmphasisAmbient
import androidx.ui.material.IconToggleButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ProvideEmphasis
import androidx.ui.material.Surface
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Bookmark
import androidx.ui.material.icons.filled.BookmarkBorder
import androidx.ui.material.icons.filled.MoreVert
import androidx.ui.material.ripple.ripple
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.furyhawk.teamfighttacticdex.R
import com.furyhawk.teamfighttacticdex.data.heroes.impl.hero1
import com.furyhawk.teamfighttacticdex.model.Hero
import com.furyhawk.teamfighttacticdex.ui.TftStatus
import com.furyhawk.teamfighttacticdex.ui.Screen
import com.furyhawk.teamfighttacticdex.ui.ThemedPreview
import com.furyhawk.teamfighttacticdex.ui.navigateTo

@Composable
fun AuthorAndReadTime(
    post: Hero,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        ProvideEmphasis(EmphasisAmbient.current.medium) {
            val textStyle = MaterialTheme.typography.body2
            Text(
                text = post.metadata.author.name,
                style = textStyle
            )
            Text(
                text = " - ${post.metadata.readTimeMinutes} min read",
                style = textStyle
            )
        }
    }
}

@Composable
fun PostImage(post: Hero, modifier: Modifier = Modifier) {
    val image = post.imageThumb ?: imageResource(R.drawable.placeholder_1_1)
    Image(
        asset = image,
        modifier = modifier
            .preferredSize(40.dp, 40.dp)
            .clip(MaterialTheme.shapes.small)
    )
}

@Composable
fun PostTitle(post: Hero) {
    ProvideEmphasis(EmphasisAmbient.current.high) {
        Text(post.title, style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun PostCardSimple(post: Hero) {
    Box(Modifier.ripple().clickable(onClick = { navigateTo(Screen.Article(post.id)) }
    ), children = {
        Row(modifier = Modifier.padding(16.dp)) {
            PostImage(post, Modifier.padding(end = 16.dp))
            Column(modifier = Modifier.weight(1f)) {
                PostTitle(post)
                AuthorAndReadTime(post)
            }
            BookmarkButton(
                isBookmarked = isFavorite(postId = post.id),
                onBookmark = { toggleBookmark(postId = post.id) }
            )
        }
    })
}

@Composable
fun PostCardHistory(post: Hero) {
    Box(Modifier.ripple().clickable(onClick = { navigateTo(Screen.Article(post.id)) }
    ), children = {
        Row(Modifier.padding(16.dp)) {
            PostImage(
                post = post,
                modifier = Modifier.padding(end = 16.dp)
            )
            Column(Modifier.weight(1f)) {
                ProvideEmphasis(EmphasisAmbient.current.medium) {
                    Text(
                        text = "BASED ON YOUR HISTORY",
                        style = MaterialTheme.typography.overline
                    )
                }
                PostTitle(post = post)
                AuthorAndReadTime(
                    post = post,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            ProvideEmphasis(EmphasisAmbient.current.medium) {
                Icon(asset = Icons.Filled.MoreVert)
            }
        }
    })
}

@Composable
fun BookmarkButton(
    isBookmarked: Boolean,
    onBookmark: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    IconToggleButton(
        checked = isBookmarked,
        onCheckedChange = onBookmark
    ) {
        modifier.fillMaxSize()
        if (isBookmarked) {
            Icon(
                asset = Icons.Filled.Bookmark,
                modifier = modifier
            )
        } else {
            Icon(
                asset = Icons.Filled.BookmarkBorder,
                modifier = modifier
            )
        }
    }
}

fun toggleBookmark(postId: String) {
    with(TftStatus) {
        if (favorites.contains(postId)) {
            favorites.remove(postId)
        } else {
            favorites.add(postId)
        }
    }
}

fun isFavorite(postId: String) = TftStatus.favorites.contains(postId)

@Preview("Bookmark Button")
@Composable
fun BookmarkButtonPreview() {
    ThemedPreview {
        Surface {
            BookmarkButton(isBookmarked = false, onBookmark = { })
        }
    }
}

@Preview("Bookmark Button Bookmarked")
@Composable
fun BookmarkButtonBookmarkedPreview() {
    ThemedPreview {
        Surface {
            BookmarkButton(isBookmarked = true, onBookmark = { })
        }
    }
}

@Preview("Simple post card")
@Composable
fun SimplePostPreview() {
    ThemedPreview {
        PostCardSimple(post = hero1)
    }
}

@Preview("Hero History card")
@Composable
fun HistoryPostPreview() {
    ThemedPreview {
        PostCardHistory(post = hero1)
    }
}

@Preview("Simple post card dark theme")
@Composable
fun SimplePostDarkPreview() {
    ThemedPreview(darkTheme = true) {
        PostCardSimple(post = hero1)
    }
}
