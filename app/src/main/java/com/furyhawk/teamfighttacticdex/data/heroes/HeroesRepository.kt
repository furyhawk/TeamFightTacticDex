package com.furyhawk.teamfighttacticdex.data.heroes

import com.furyhawk.teamfighttacticdex.data.Result
import com.furyhawk.teamfighttacticdex.model.Hero

interface HeroesRepository {

    /**
     * Get a specific tft Hero.
     */
    fun getHero(postId: String, callback: (Result<Hero?>) -> Unit)

    /**
     * Get tft Heroes.
     */
    fun getHeroes(callback: (Result<List<Hero>>) -> Unit)
}