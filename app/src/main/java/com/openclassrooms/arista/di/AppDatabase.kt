package com.openclassrooms.arista.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.arista.data.dao.ExerciseDtoDao
import com.openclassrooms.arista.data.dao.SleepDtoDao
import com.openclassrooms.arista.data.dao.UserDtoDao
import com.openclassrooms.arista.data.entity.ExerciseDto
import com.openclassrooms.arista.data.entity.SleepDto
import com.openclassrooms.arista.data.entity.UserDto
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.reflect.KParameter

@Database(
    entities = [UserDto::class, SleepDto::class, ExerciseDto::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDtoDao(): UserDtoDao
    abstract fun sleepDtoDao(): SleepDtoDao
    abstract fun exerciseDtoDao(): ExerciseDtoDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.sleepDtoDao(), database.exerciseDtoDao(),database.userDtoDao())
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, coroutineScope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "AristaDB"
                )
                    .addCallback(AppDatabaseCallback(coroutineScope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        suspend fun populateDatabase(
            sleepDao: SleepDtoDao,
            exerciseDtoDao: ExerciseDtoDao,
            userDtoDao: UserDtoDao
        ) {

            exerciseDtoDao.insertExercise(
                ExerciseDto(
                    startTime = LocalDateTime.now().minusHours(5).toEpochSecond(ZoneOffset.UTC),
                    duration = 30,
                    category = ExerciseCategory.Running.name,
                    intensity = 7
                )
            )
            exerciseDtoDao.insertExercise(
                ExerciseDto(
                    startTime = LocalDateTime.now().minusDays(1).minusHours(3).toEpochSecond(ZoneOffset.UTC),
                    duration = 45,
                    category = ExerciseCategory.Swimming.name,
                    intensity = 6
                )
            )
            exerciseDtoDao.insertExercise(
                ExerciseDto(
                    startTime = LocalDateTime.now().minusDays(2).minusHours(4).toEpochSecond(ZoneOffset.UTC),
                    duration = 60,
                    category = ExerciseCategory.Football.name,
                    intensity = 8
                )
            )


            sleepDao.insertSleep(
                SleepDto(
                    startTime = LocalDateTime.now().minusDays(1).atZone(ZoneOffset.UTC).toInstant()
                        .toEpochMilli(), duration = 480, quality = 4
                )
            )

            sleepDao.insertSleep(
                SleepDto(
                    startTime =
                    LocalDateTime.now().minusDays(2).atZone(ZoneOffset.UTC).toInstant()
                        .toEpochMilli(), duration = 450, quality = 3
                )
            )

            userDtoDao.insertUser(
                UserDto(
                    name = "John Doe",
                    email = "johndoe@example.com",
                    password = ""
                )
            )
        }
    }
}