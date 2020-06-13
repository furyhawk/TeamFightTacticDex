package com.furyhawk.teamfighttacticdex.ui.home

import android.os.Handler
import android.os.Looper
import androidx.compose.Composable
import androidx.compose.onActive
import androidx.compose.remember
import androidx.compose.stateFor
import androidx.core.os.postDelayed
import androidx.lifecycle.LiveData
import androidx.ui.core.Alignment
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.ripple.ripple
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.furyhawk.teamfighttacticdex.R
import com.furyhawk.teamfighttacticdex.data.Champion
import com.furyhawk.teamfighttacticdex.data.ChampionRepository
import com.furyhawk.teamfighttacticdex.data.heroes.HeroesRepository
import com.furyhawk.teamfighttacticdex.data.heroes.impl.BlockingFakeHeroesRepository
import com.furyhawk.teamfighttacticdex.model.Hero
import com.furyhawk.teamfighttacticdex.ui.*
import com.furyhawk.teamfighttacticdex.ui.state.*
import com.furyhawk.teamfighttacticdex.ui.theme.snackbarAction

@Composable
fun HomeScreen(
    heroesRepository: HeroesRepository,
    championRepository: ChampionRepository? = null,
    scaffoldState: ScaffoldState = remember { ScaffoldState() }
) {
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            AppDrawer(
                currentScreen = Screen.Home,
                closeDrawer = { scaffoldState.drawerState = DrawerState.Closed }
            )
        },
        topAppBar = {
            TopAppBar(
                title = { Text(text = "Team Fight Tactic Dex") },
                navigationIcon = {
                    IconButton(onClick = { scaffoldState.drawerState = DrawerState.Opened }) {
                        Icon(vectorResource(R.drawable.ic_tft_logo))
                    }
                }
            )
        },
        bodyContent = { modifier ->
            HomeScreenContent(
                heroesRepository = heroesRepository,
                championRepository = championRepository,
                modifier = modifier
            )
        }
    )
}


@Composable
private fun HomeScreenContent(
    heroesRepository: HeroesRepository,
    championRepository: ChampionRepository? = null,
    modifier: Modifier = Modifier
) {
    val (postsState, refreshPosts) = refreshableUiStateFrom(heroesRepository::getHeroes)
    val champions = championRepository?.getChampions()

    if (postsState.loading && !postsState.refreshing) {
        LoadingHomeScreen()
    } else {
        SwipeToRefreshLayout(
            refreshingState = postsState.refreshing,
            onRefresh = { refreshPosts() },
            refreshIndicator = {
                Surface(elevation = 10.dp, shape = CircleShape) {
                    CircularProgressIndicator(Modifier.preferredSize(50.dp).padding(4.dp))
                }
            }
        ) {
            HomeScreenBodyWrapper(
                modifier = modifier,
                state = postsState,
                onErrorAction = {
                    refreshPosts()
                },
                champions = champions
            )
        }
    }
}


@Composable
private fun HomeScreenBodyWrapper(
    modifier: Modifier = Modifier,
    state: RefreshableUiState<List<Hero>>,
    onErrorAction: () -> Unit,
    champions: LiveData<List<Champion>>? = null
) {
    // State for showing the Snackbar error. This state will reset with the content of the lambda
    // inside stateFor each time the RefreshableUiState input parameter changes.
    // showSnackbarError is the value of the error state, use updateShowSnackbarError to update it.
    val (showSnackbarError, updateShowSnackbarError) = stateFor(state) {
        state is RefreshableUiState.Error
    }

    Stack(modifier = modifier.fillMaxSize()) {
        state.currentData?.let { posts ->
            HomeScreenBody(
                posts = posts,
                champions = champions
            )
        }
        ErrorSnackbar(
            showError = showSnackbarError,
            onErrorAction = onErrorAction,
            onDismiss = { updateShowSnackbarError(false) },
            modifier = Modifier.gravity(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun HomeScreenBody(
    posts: List<Hero>,
    modifier: Modifier = Modifier,
    champions: LiveData<List<Champion>>? = null
) {
    val postTop = posts[3]
    val postsSimple = posts.subList(0, 2)
    val postsPopular = posts.subList(2, 7)
    val postsHistory = posts.subList(7, 10)

    VerticalScroller {
        Column(modifier) {
            if (!champions?.value.isNullOrEmpty()) {
//                HomeScreenChampion(champions)
            }

            HomeScreenTopSection(postTop)
            HomeScreenSimpleSection(postsSimple)
            HomeScreenPopularSection(postsPopular)
            HomeScreenHistorySection(postsHistory)
        }
    }
}

@Composable
private fun HomeScreenChampion(posts: LiveData<List<Champion>>?) {
    Column {
        if (posts != null) {
            posts.value?.forEach { post ->
                PostCardChampion(post)
                HomeScreenDivider()  }
        }
//        posts.forEach { post ->
//            PostCardChampion(post)
//            HomeScreenDivider()
//        }
    }
}


@Composable
private fun LoadingHomeScreen() {
    Box(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
        CircularProgressIndicator()
    }
}


@Composable
fun ErrorSnackbar(
    showError: Boolean,
    modifier: Modifier = Modifier,
    onErrorAction: () -> Unit = { },
    onDismiss: () -> Unit = { }
) {
    if (showError) {
        // Make Snackbar disappear after 5 seconds if the user hasn't interacted with it
        onActive {
            // With coroutines, this will be cancellable
            Handler(Looper.getMainLooper()).postDelayed(5000L) {
                onDismiss()
            }
        }

        Snackbar(
            modifier = modifier.padding(16.dp),
            text = { Text("Can't update latest news") },
            action = {
                TextButton(
                    onClick = {
                        onErrorAction()
                        onDismiss()
                    },
                    contentColor = contentColor()
                ) {
                    Text(
                        text = "RETRY",
                        color = MaterialTheme.colors.snackbarAction
                    )
                }
            }
        )
    }
}

@Composable
private fun HomeScreenTopSection(post: Hero) {
    ProvideEmphasis(EmphasisAmbient.current.high) {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
            text = "Top stories for you",
            style = MaterialTheme.typography.subtitle1
        )
    }
    Box(Modifier.ripple().clickable(onClick = { navigateTo(Screen.Article(post.id)) }), children = {
        PostCardTop(post = post)
    })
    HomeScreenDivider()
}

@Composable
private fun HomeScreenSimpleSection(posts: List<Hero>) {
    Column {
        posts.forEach { post ->
            PostCardSimple(post)
            HomeScreenDivider()
        }
    }
}

@Composable
private fun HomeScreenPopularSection(posts: List<Hero>) {
    Column {
        ProvideEmphasis(EmphasisAmbient.current.high) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Popular on Jetnews",
                style = MaterialTheme.typography.subtitle1
            )
        }
        HorizontalScroller {
            Row(modifier = Modifier.padding(end = 16.dp, bottom = 16.dp)) {
                posts.forEach { post ->
                    PostCardPopular(post, Modifier.padding(start = 16.dp))
                }
            }
        }
        HomeScreenDivider()
    }
}

@Composable
private fun HomeScreenHistorySection(posts: List<Hero>) {
    Column {
        posts.forEach { post ->
            PostCardHistory(post)
            HomeScreenDivider()
        }
    }
}

@Composable
private fun HomeScreenDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
    )
}

@Preview("Home screen body")
@Composable
fun PreviewHomeScreenBody() {
    ThemedPreview {
        val posts = loadFakePosts()
        HomeScreenBody(posts)
    }
}

@Preview("Home screen, open drawer")
@Composable
private fun PreviewDrawerOpen() {
    ThemedPreview {
        HomeScreen(
            heroesRepository = BlockingFakeHeroesRepository(ContextAmbient.current),
            scaffoldState = ScaffoldState(drawerState = DrawerState.Opened)
        )
    }
}

@Preview("Home screen dark theme")
@Composable
fun PreviewHomeScreenBodyDark() {
    ThemedPreview(darkTheme = true) {
        val posts = loadFakePosts()
        HomeScreenBody(posts)
    }
}

@Preview("Home screen, open drawer dark theme")
@Composable
private fun PreviewDrawerOpenDark() {
    ThemedPreview(darkTheme = true) {
        HomeScreen(
            heroesRepository = BlockingFakeHeroesRepository(ContextAmbient.current),
            scaffoldState = ScaffoldState(drawerState = DrawerState.Opened)
        )
    }
}

@Composable
private fun loadFakePosts(): List<Hero> {
    return previewDataFrom(BlockingFakeHeroesRepository(ContextAmbient.current)::getHeroes)
}
