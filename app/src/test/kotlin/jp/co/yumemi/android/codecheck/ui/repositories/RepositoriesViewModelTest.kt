package jp.co.yumemi.android.codecheck.ui.repositories

import jp.co.yumemi.android.codecheck.MainDispatcherRule
import jp.co.yumemi.android.codecheck.data.repository.impl.FakeGithubRepoRepositoryImpl

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class RepositoriesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val repository = FakeGithubRepoRepositoryImpl()
    private lateinit var viewModel: RepositoriesViewModel

    private val query = "flutter"

    @Before
    fun setUp() {
        viewModel = RepositoriesViewModel(repository)
    }

    @Test
    fun stateIsInit() = runTest {
        assertEquals(viewModel.uiState.value, UiState.Initial)
    }

    @Test
    fun stateIsSuccess() = runTest {
        repository.isSuccess = true
        val job = launch(mainDispatcherRule.testDispatcher) {
            viewModel.searchResults(query)
        }
        assertEquals(viewModel.uiState.value, UiState.Data(repository.successData))
        job.cancel()
    }

    @Test
    fun stateIsError() = runTest {
        repository.isSuccess = false
        val job = launch(mainDispatcherRule.testDispatcher) {
            viewModel.searchResults(query)
        }
        assertEquals(viewModel.uiState.value, UiState.Error(repository.errorData))
        job.cancel()
    }
}