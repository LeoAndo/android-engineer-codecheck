package jp.co.yumemi.android.code_check.data.repository.impl

import jp.co.yumemi.android.code_check.data.api.GithubApi
import jp.co.yumemi.android.code_check.data.dataOrThrow
import jp.co.yumemi.android.code_check.di.IoDispatcher
import jp.co.yumemi.android.code_check.data.repository.GithubRepoRepository
import jp.co.yumemi.android.code_check.model.RepositorySummary
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GithubRepoRepositoryImpl @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val api: GithubApi,
) : GithubRepoRepository {
    override suspend fun searchRepositories(query: String): List<RepositorySummary> {
        return dataOrThrow(dispatcher) { api.searchRepositories(query) }
    }
}