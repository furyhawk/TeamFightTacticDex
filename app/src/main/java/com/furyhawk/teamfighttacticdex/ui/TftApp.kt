package com.furyhawk.teamfighttacticdex.ui

import androidx.compose.Composable
import androidx.ui.animation.Crossfade
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.graphics.ColorFilter
import androidx.ui.graphics.vector.VectorAsset
import androidx.ui.layout.*
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.material.TextButton
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Home
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.furyhawk.teamfighttacticdex.R
import com.furyhawk.teamfighttacticdex.data.AppContainer
import com.furyhawk.teamfighttacticdex.data.heroes.HeroesRepository
import com.furyhawk.teamfighttacticdex.ui.home.HomeScreen
import com.furyhawk.teamfighttacticdex.ui.theme.TftTheme


@Composable
fun TftApp(appContainer: AppContainer) {
    TftTheme {
        AppContent(
            heroesRepository = appContainer.heroesRepository
        )
    }
}

@Composable
private fun AppContent(
    heroesRepository: HeroesRepository
) {

    Crossfade(TftStatus.currentScreen) { screen ->
        Surface(color = MaterialTheme.colors.background) {
            when (screen) {
                is Screen.Home -> HomeScreen(heroesRepository = heroesRepository)
//                is Screen.Article -> ArticleScreen(
//                        postId = screen.postId,
//                        heroesRepository = heroesRepository
//                )
            }
        }
    }
}

@Composable
fun AppDrawer(
    currentScreen: Screen,
    closeDrawer: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(Modifier.preferredHeight(24.dp))
        TftLogo(Modifier.padding(16.dp))
        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = .2f))
        DrawerButton(
            icon = Icons.Filled.Home,
            label = "Home",
            isSelected = currentScreen == Screen.Home,
            action = {
                navigateTo(Screen.Home)
                closeDrawer()
            }
        )
    }
}

@Composable
private fun TftLogo(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Image(
            asset = vectorResource(R.drawable.ic_tft_logo),
            colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
        )
        Spacer(Modifier.preferredWidth(8.dp))
        Image(
            asset = vectorResource(R.drawable.ic_tft_logo),
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
        )
    }
}

@Composable
private fun DrawerButton(
    icon: VectorAsset,
    label: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colors
    val imageAlpha = if (isSelected) {
        1f
    } else {
        0.6f
    }
    val textIconColor = if (isSelected) {
        colors.primary
    } else {
        colors.onSurface.copy(alpha = 0.6f)
    }
    val backgroundColor = if (isSelected) {
        colors.primary.copy(alpha = 0.12f)
    } else {
        colors.surface
    }

    val surfaceModifier = modifier
        .padding(start = 8.dp, top = 8.dp, end = 8.dp)
        .fillMaxWidth()
    Surface(
        modifier = surfaceModifier,
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {
        TextButton(
            onClick = action,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalGravity = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()) {
                Image(
                    asset = icon,
                    colorFilter = ColorFilter.tint(textIconColor),
                    alpha = imageAlpha
                )
                Spacer(Modifier.preferredWidth(16.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.body2,
                    color = textIconColor,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
