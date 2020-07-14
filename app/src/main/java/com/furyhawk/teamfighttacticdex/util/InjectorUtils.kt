package com.furyhawk.teamfighttacticdex.util

import android.content.Context
import com.furyhawk.teamfighttacticdex.data.AppDatabase
import com.furyhawk.teamfighttacticdex.data.ChampionRepository
import com.furyhawk.teamfighttacticdex.viewmodels.ChampionListViewModelFactory

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {
    private fun getChampionRepository(context: Context): ChampionRepository {
        return ChampionRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).championDao())
    }
    fun provideGardenPlantingListViewModelFactory(
        context: Context
    ): ChampionListViewModelFactory {
        val repository = getChampionRepository(context)
        return ChampionListViewModelFactory(repository)
    }
}