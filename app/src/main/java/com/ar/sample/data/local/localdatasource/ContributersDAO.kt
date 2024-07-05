package com.ar.sample.data.local.localdatasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.ar.sample.data.models.GithubContributors
import kotlinx.coroutines.flow.Flow

@Dao
interface ContributersDAO {
    @Query("SELECT * FROM GithubContributors")
    fun getAllContributers(): Flow<List<GithubContributors>>

    @Insert
    suspend fun insert(advertisement: GithubContributors)

    @Query("DELETE FROM GithubContributors")
    suspend fun deleteAll()

    @Transaction
    suspend fun deleteAllAndInsertList(
        contributersList: List<GithubContributors>,
        isClear: Boolean
    ) {
        if (isClear) {
            deleteAll()
        }
        contributersList.forEach { insert(it) }
    }
}