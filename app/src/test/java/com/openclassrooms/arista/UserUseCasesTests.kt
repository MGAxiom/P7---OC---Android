package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.UserRepository
import com.openclassrooms.arista.domain.model.User
import com.openclassrooms.arista.domain.usecase.GetUserUsecase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UserUseCasesTests {
    @Mock
    private lateinit var repository: UserRepository

    private lateinit var getUserUsecase: GetUserUsecase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getUserUsecase = GetUserUsecase(repository)
    }

    @Test
    fun `test should return expected user`(): Unit = runBlocking {
        val expectedUser = User("John", "john@example.com")

        `when`(repository.getUser()).thenReturn(expectedUser)

        // When
        val result = getUserUsecase.execute()

        // Then
        assertEquals(expectedUser, result)
        verify(repository).getUser()
    }
}