package com.furyhawk.teamfighttacticdex.data

import android.content.Context
import android.os.Handler
import android.os.Looper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
//    val postsRepository: PostsRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class AppContainerImpl(private val applicationContext: Context) : AppContainer {

    private val executorService: ExecutorService by lazy {
        Executors.newFixedThreadPool(4)
    }

    private val mainThreadHandler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

//    override val postsRepository: PostsRepository by lazy {
//        FakePostsRepository(
//                executorService = executorService,
//                resultThreadHandler = mainThreadHandler,
//                resources = applicationContext.resources
//        )
//    }


}
