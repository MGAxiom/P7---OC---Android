package com.openclassrooms.arista.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.openclassrooms.arista.data.entity.UserDto

@Dao
interface UserDtoDao {
    @Query("SELECT * FROM user")
    fun getUsers(): UserDto

}