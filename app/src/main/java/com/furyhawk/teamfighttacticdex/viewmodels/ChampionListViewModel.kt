package com.furyhawk.teamfighttacticdex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.furyhawk.teamfighttacticdex.data.Champion
import com.furyhawk.teamfighttacticdex.data.ChampionRepository

class ChampionListViewModel internal constructor(
    championRepository: ChampionRepository
) : ViewModel() {
    val champions: LiveData<List<Champion>> =
        championRepository.getChampions()
}
