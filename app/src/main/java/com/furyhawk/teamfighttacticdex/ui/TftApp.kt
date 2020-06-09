package com.furyhawk.teamfighttacticdex.ui

import androidx.compose.Composable
import androidx.ui.foundation.Text
import com.furyhawk.teamfighttacticdex.data.AppContainer
import com.furyhawk.teamfighttacticdex.ui.theme.TftTheme


@Composable
fun TftApp(appContainer: AppContainer) {
    TftTheme {
        AppContent(
//                postsRepository = appContainer.postsRepository
        )
    }
}

@Composable
private fun AppContent(
//        postsRepository: PostsRepository
) {
    Text(text = "Hello!")
//    Crossfade(JetnewsStatus.currentScreen) { screen ->
//        Surface(color = MaterialTheme.colors.background) {
//            when (screen) {
//                is Screen.Home -> HomeScreen(postsRepository = postsRepository)
//                is Screen.Article -> ArticleScreen(
//                        postId = screen.postId,
//                        postsRepository = postsRepository
//                )
//            }
//        }
//    }
}

//@Composable
//fun Greeting(name: String) {
//    Text(text = "Hello $name!")
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    AppTheme {
//        Greeting("Android")
//    }
//}