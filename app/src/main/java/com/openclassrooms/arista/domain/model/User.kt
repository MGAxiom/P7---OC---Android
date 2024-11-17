package com.openclassrooms.arista.domain.model

import com.openclassrooms.arista.data.entity.UserDto

data class User(
    var name: String,
    var email: String
) {

    fun toDto(): UserDto {
        return UserDto(
            name = this.name,
            email = this.email,
            password = ""
        )
    }

    companion object {
        fun fromDto(dto: UserDto): User {
            return User(
                dto.name,
                dto.email
            )
        }
    }
}