package com.openclassrooms.arista.domain.model

import com.openclassrooms.arista.data.entity.SleepDto
import java.time.LocalDateTime
import java.time.ZoneOffset

data class Sleep(
    var id: Long? = null,
    @JvmField var startTime: LocalDateTime,
    var duration: Int,
    var quality: Int
) {
    fun toDto(): SleepDto {
        return SleepDto(
            startTime = this.startTime.toEpochSecond(ZoneOffset.UTC) * 1000, // Convert to milliseconds
            duration = this.duration,
            quality = this.quality
        )
    }

    companion object {
        fun fromDto(dto: SleepDto): Sleep {
            return Sleep(
                id = dto.id,
                LocalDateTime.ofEpochSecond(dto.startTime / 1000, 0, ZoneOffset.UTC),
                dto.duration,
                dto.quality
            )
        }
    }
}
