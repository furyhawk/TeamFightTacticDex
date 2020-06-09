package com.furyhawk.teamfighttacticdex.data.heroes.impl

import android.content.res.Resources
import android.os.Handler
import androidx.ui.graphics.imageFromResource
import com.furyhawk.teamfighttacticdex.data.Result
import com.furyhawk.teamfighttacticdex.data.heroes.HeroesRepository
import com.furyhawk.teamfighttacticdex.model.Hero
import java.util.concurrent.ExecutorService
import kotlin.random.Random

class FakeHeroesRepository(
    private val executorService: ExecutorService,
    private val resultThreadHandler: Handler,
    private val resources: Resources
) : HeroesRepository {
    /**
     * Simulates preparing the data for each post.
     *
     * DISCLAIMER: Loading resources with the ApplicationContext isn't ideal as it isn't themed.
     * This should be done from the UI layer.
     */
    private val postsWithResources: List<Hero> by lazy {
        heroes.map {
            it.copy(
                image = imageFromResource(resources, it.imageId),
                imageThumb = imageFromResource(resources, it.imageThumbId)
            )
        }
    }

    override fun getHero(postId: String, callback: (Result<Hero?>) -> Unit) {
        executeInBackground(callback) {
            resultThreadHandler.post {
                callback(Result.Success(
                    postsWithResources.find { it.id == postId }
                ))
            }
        }
    }

    override fun getHeroes(callback: (Result<List<Hero>>) -> Unit) {
        executeInBackground(callback) {
            simulateNetworkRequest()
            Thread.sleep(1500L)
            if (shouldRandomlyFail()) {
                throw IllegalStateException()
            }
            resultThreadHandler.post { callback(Result.Success(postsWithResources)) }
        }
    }

    /**
     * Executes a block of code in the past and returns an error in the [callback]
     * if [block] throws an exception.
     */
    private fun executeInBackground(callback: (Result<Nothing>) -> Unit, block: () -> Unit) {
        executorService.execute {
            try {
                block()
            } catch (e: Exception) {
                resultThreadHandler.post { callback(Result.Error(e)) }
            }
        }
    }

    /**
     * Simulates network request
     */
    private var networkRequestDone = false
    private fun simulateNetworkRequest() {
        if (!networkRequestDone) {
            Thread.sleep(2000L)
            networkRequestDone = true
        }
    }

    /**
     * 1/3 requests should fail loading
     */
    private fun shouldRandomlyFail(): Boolean = Random.nextFloat() < 0.33f
}