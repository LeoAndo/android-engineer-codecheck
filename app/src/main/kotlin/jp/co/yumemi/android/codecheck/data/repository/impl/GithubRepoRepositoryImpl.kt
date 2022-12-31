package jp.co.yumemi.android.codecheck.data.repository.impl

import jp.co.yumemi.android.codecheck.data.api.GithubApi
import jp.co.yumemi.android.codecheck.data.dataOrThrow
import jp.co.yumemi.android.codecheck.di.IoDispatcher
import jp.co.yumemi.android.codecheck.data.repository.GithubRepoRepository
import jp.co.yumemi.android.codecheck.model.RepositorySummary
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