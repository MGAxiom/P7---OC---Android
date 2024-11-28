package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.usecase.AddNewExerciseUseCase
import com.openclassrooms.arista.domain.usecase.DeleteExerciseUseCase
import com.openclassrooms.arista.domain.usecase.GetAllExercisesUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

class ExerciseUseCasesTests {
    @Mock
    private lateinit var repository: ExerciseRepository

    private lateinit var getAllExerciseUsecase: GetAllExercisesUseCase
    private lateinit var deleteExerciseUseCase: DeleteExerciseUseCase
    private lateinit var addExerciseUsecase: AddNewExerciseUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getAllExerciseUsecase = GetAllExercisesUseCase(repository)
        deleteExerciseUseCase = DeleteExerciseUseCase(repository)
        addExerciseUsecase = AddNewExerciseUseCase(repository)
    }

    @Test
    fun `test should return expected added exercise`(): Unit = runBlocking {
        val expectedExercise = listOf(
            Exercise(
                id = 1,
                startTime = LocalDateTime.now(),
                duration = 30,
                category = ExerciseCategory.Running,
                intensity = 5
            )
        )

        `when`(repository.getAllExercises()).thenReturn(expectedExercise)

        // When
        val result = getAllExerciseUsecase.execute()

        // Then
        assertEquals(expectedExercise, result)
        verify(repository).getAllExercises()
    }

    @Test
    fun `test should return empty list of exercises after deletion`() = runBlocking {
        // Given
        val exercise = listOf(
            Exercise(
                id = 1,
                startTime = LocalDateTime.now(),
                duration = 30,
                category = ExerciseCategory.Running,
                intensity = 5
            )
        )
        `when`(repository.getAllExercises()).thenReturn(exercise)

        // When
        deleteExerciseUseCase.execute(exercise[0])
        `when`(repository.getAllExercises()).thenReturn(emptyList())
        val result = getAllExerciseUsecase.execute()

        // Then
        assertTrue(result.isEmpty())
        verify(repository, times(1)).getAllExercises()
        verify(repository).deleteExercise(exercise[0])
    }


}