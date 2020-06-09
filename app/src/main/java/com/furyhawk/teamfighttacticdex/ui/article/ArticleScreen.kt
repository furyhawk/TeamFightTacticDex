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

package com.furyhawk.teamfighttacticdex.ui.article

import android.content.Context
import android.content.Intent
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.contentColor
import androidx.ui.layout.Row
import androidx.ui.layout.Spacer
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.preferredHeight
import androidx.ui.material.AlertDialog
import androidx.ui.material.IconButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Scaffold
import androidx.ui.material.Surface
import androidx.ui.material.TextButton
import androidx.ui.material.TopAppBar
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.ArrowBack
import androidx.ui.material.icons.filled.FavoriteBorder
import androidx.ui.material.icons.filled.Share
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.furyhawk.teamfighttacticdex.R
import com.furyhawk.teamfighttacticdex.data.heroes.HeroesRepository
import com.furyhawk.teamfighttacticdex.data.heroes.impl.BlockingFakeHeroesRepository
import com.furyhawk.teamfighttacticdex.data.heroes.impl.hero3
import com.furyhawk.teamfighttacticdex.data.successOr
import com.furyhawk.teamfighttacticdex.model.Hero
import com.furyhawk.teamfighttacticdex.ui.Screen
import com.furyhawk.teamfighttacticdex.ui.ThemedPreview
import com.furyhawk.teamfighttacticdex.ui.effect.fetchPost
import com.furyhawk.teamfighttacticdex.ui.home.BookmarkButton
import com.furyhawk.teamfighttacticdex.ui.home.isFavorite
import com.furyhawk.teamfighttacticdex.ui.home.toggleBookmark
import com.furyhawk.teamfighttacticdex.ui.navigateTo
import com.furyhawk.teamfighttacticdex.ui.state.UiState

@Composable
fun ArticleScreen(postId: String, postsRepository: HeroesRepository) {
    val postsState = fetchPost(postId, postsRepository)
    if (postsState is UiState.Success<Hero>) {
        ArticleScreen(postsState.data)
    }
}

@Composable
private fun ArticleScreen(post: Hero) {

    var showDialog by state { false }
    if (showDialog) {
        FunctionalityNotAvailablePopup { showDialog = false }
    }

    Scaffold(
        topAppBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Published in: ${post.publication?.name}",
                        style = MaterialTheme.typography.subtitle2,
                        color = contentColor()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navigateTo(Screen.Home) }) {
                        Icon(Icons.Filled.ArrowBack)
                    }
                }
            )
        },
        bodyContent = { modifier ->
            PostContent(post, modifier)
        },
        bottomAppBar = {
            BottomBar(post) { showDialog = true }
        }
    )
}

@Composable
private fun BottomBar(post: Hero, onUnimplementedAction: () -> Unit) {
    Surface(elevation = 2.dp) {
            Row(
                verticalGravity = Alignment.CenterVertically,
                modifier = Modifier
                    .preferredHeight(56.dp)
                    .fillMaxWidth()
            ) {
                IconButton(onClick = onUnimplementedAction) {
                    Icon(Icons.Filled.FavoriteBorder)
                }
                BookmarkButton(
                    isBookmarked = isFavorite(postId = post.id),
                    onBookmark = { toggleBookmark(postId = post.id) }
                )
                val context = ContextAmbient.current
                IconButton(onClick = { sharePost(post, context) }) {
                    Icon(Icons.Filled.Share)
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onUnimplementedAction) {
                    Icon(vectorResource(R.drawable.ic_text_settings))
                }
            }
        }
}

@Composable
private fun FunctionalityNotAvailablePopup(onDismiss: () -> Unit) {
    AlertDialog(
        onCloseRequest = onDismiss,
        text = {
            Text(
                text = "Functionality not available \uD83D\uDE48",
                style = MaterialTheme.typography.body2
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "CLOSE")
            }
        }
    )
}

private fun sharePost(post: Hero, context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TITLE, post.title)
        putExtra(Intent.EXTRA_TEXT, post.url)
    }
    context.startActivity(Intent.createChooser(intent, "Share post"))
}

@Preview("Article screen")
@Composable
fun PreviewArticle() {
    ThemedPreview {
        val hero = loadFakePost(hero3.id)
        ArticleScreen(hero)
    }
}

@Preview("Article screen dark theme")
@Composable
fun PreviewArticleDark() {
    ThemedPreview(darkTheme = true) {
        val hero = loadFakePost(hero3.id)
        ArticleScreen(hero)
    }
}

@Composable
private fun loadFakePost(postId: String): Hero {
    var hero: Hero? = null
    BlockingFakeHeroesRepository(ContextAmbient.current).getHero(postId) { result ->
        hero = result.successOr(null)
    }
    return hero!!
}
