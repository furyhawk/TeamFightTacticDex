package com.furyhawk.teamfighttacticdex

import android.view.MenuItem
import androidx.compose.Composable
import com.furyhawk.teamfighttacticdex.home.Home
import com.github.zsoltk.compose.router.Router

interface Root {

    sealed class Routing {
        object Home : Routing()
        object Tftdex : Routing()
    }

    companion object {
        @Composable
        fun Content(defaultRouting: Routing = Routing.Home) {
            Router(defaultRouting) { backStack ->
                val onMenuItemSelected: (MenuItem) -> Unit = {
                    when (it) {
                        MenuItem.Tftdex -> backStack.push(Routing.Tftdex)
                    }
                }

                when (val routing = backStack.last()) {
                    is Routing.Home -> Home.Content(onMenuItemSelected)
                    is Routing.Tftdex -> Tftdex.Content()
                }
            }
        }
    }
}
