package com.openclassrooms.arista.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.arista.data.entity.SleepDto
import kotlinx.coroutines.flow.Flow

@Dao
interface SleepDtoDao {

    @Insert
    suspend fun insertSleep(sleepDto: SleepDto)

    @Query("SELECT * FROM sleep")
    fun getAllSleeps(): Flow<List<SleepDto>>

    @Query("DELETE FROM sleep")
    suspend fun deleteSleepById(sleepId: Long)
}