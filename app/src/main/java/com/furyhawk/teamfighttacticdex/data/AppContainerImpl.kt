package com.furyhawk.teamfighttacticdex.data

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.ui.core.ContextAmbient
import com.furyhawk.teamfighttacticdex.data.heroes.HeroesRepository
import com.furyhawk.teamfighttacticdex.data.heroes.impl.BlockingFakeHeroesRepository
import com.furyhawk.teamfighttacticdex.data.heroes.impl.FakeHeroesRepository
import com.furyhawk.teamfighttacticdex.util.InjectorUtils
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val heroesRepository: HeroesRepository
    val championRepository: ChampionRepository
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

    override val heroesRepository: HeroesRepository by lazy {
        FakeHeroesRepository(
            executorService = executorService,
            resultThreadHandler = mainThreadHandler,
            resources = applicationContext.resources
        )
    }

    override val championRepository: ChampionRepository = ChampionRepository.getInstance(
        AppDatabase.getInstance(applicationContext.applicationContext).championDao()
    )
//    override val championRepository: ChampionRepository by lazy {
//        ChampionRepository(context.applicationContext).championDao())
//    }
//}
//        ChampionRepository.getInstance(
//            AppDatabase.getInstance(applicationContext).championDao())
//        BlockingFakeHeroesRepository(ContextAmbient.current).getHero(postId) { result ->
//            hero = result.successOr(null)
//        }
//    }
}
