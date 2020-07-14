package com.furyhawk.teamfighttacticdex.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.furyhawk.teamfighttacticdex.data.ChampionRepository

class ChampionListViewModelFactory(
    private val repository: ChampionRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChampionListViewModel(repository) as T
    }
}