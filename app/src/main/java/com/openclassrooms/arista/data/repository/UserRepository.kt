package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.dao.UserDtoDao
import com.openclassrooms.arista.domain.model.User

class UserRepository(private val userDao: UserDtoDao) {

    fun getUser(): User? {
        return userDao.getUser().let { User.fromDto(it) }
    }

    suspend fun updateUser(user: User) {
        val userDto = user.toDto()
        userDao.updateUser(userDto)
    }
}