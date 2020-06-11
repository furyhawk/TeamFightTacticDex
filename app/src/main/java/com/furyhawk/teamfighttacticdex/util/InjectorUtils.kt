package com.furyhawk.teamfighttacticdex.util

import android.content.Context
import com.furyhawk.teamfighttacticdex.data.AppDatabase
import com.furyhawk.teamfighttacticdex.data.ChampionRepository

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
class InjectorUtils {
    private fun getChampionRepository(context: Context): ChampionRepository {
        return ChampionRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).championDao())
    }
}