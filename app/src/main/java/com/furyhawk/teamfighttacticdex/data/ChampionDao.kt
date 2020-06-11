/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.furyhawk.teamfighttacticdex.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * The Data Access Object for the Plant class.
 */
@Dao
interface ChampionDao {
    @Query("SELECT * FROM champions ORDER BY name")
    fun getChampions(): LiveData<List<Champion>>

    @Query("SELECT * FROM champions WHERE id = :championId")
    fun getChampion(championId: String): LiveData<Champion>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(champions: List<Champion>)
}
