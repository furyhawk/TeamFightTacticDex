/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.furyhawk.teamfighttacticdex.ui.effect

import androidx.compose.*
import com.furyhawk.teamfighttacticdex.data.Result
import com.furyhawk.teamfighttacticdex.data.heroes.HeroesRepository
import com.furyhawk.teamfighttacticdex.model.Hero
import com.furyhawk.teamfighttacticdex.ui.state.UiState

/**
 * Effect that interacts with the repository to obtain a post with postId to display on the screen.
 *
 * To load asynchronous data, effects are better pattern than using @Model classes since
 * effects are Compose lifecycle aware.
 *
 * FIXME: Is it possible to reuse uiStateFrom for this use case?
 */
@Composable
fun fetchPost(postId: String, heroesRepository: HeroesRepository): UiState<Hero> {

    var uiState: UiState<Hero> by state<UiState<Hero>> { UiState.Loading }

    // Whenever this effect is used in a composable function, it'll load data from the repository
    // when the first composition is applied
    onActive {
        heroesRepository.getHero(postId) { result ->
            uiState = when (result) {
                is Result.Success -> {
                    if (result.data != null) {
                        UiState.Success(result.data)
                    } else {
                        UiState.Error(Exception("postId doesn't exist"))
                    }
                }
                is Result.Error -> UiState.Error(result.exception)
            }
        }
    }

    return uiState
}
