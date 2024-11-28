package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.SleepRepository
import com.openclassrooms.arista.data.repository.UserRepository
import com.openclassrooms.arista.domain.model.Sleep
import com.openclassrooms.arista.domain.usecase.GetAllSleepsUseCase
import com.openclassrooms.arista.domain.usecase.GetUserUsecase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

class SleepUseCasesTests {
    @Mock
    private lateinit var repository: SleepRepository

    private lateinit var getAllSleepUsecase: GetAllSleepsUseCase

    @Before
    fun Setup() {
        MockitoAnnotations.openMocks(this)
        getAllSleepUsecase = GetAllSleepsUseCase(repository)
    }

    @Test
    fun `test should return expected sleep`(): Unit = runBlocking {
        val expectedSleep =
            listOf(
                Sleep(
                    id = 1,
                    startTime = LocalDateTime.now(),
                    duration = 60,
                    quality = 4
                )
            )

        `when`(repository.getAllSleeps()).thenReturn(expectedSleep)

        // When
        val result = getAllSleepUsecase.execute()

        // Then
        assertEquals(expectedSleep, result)
        verify(repository).getAllSleeps()
    }
}