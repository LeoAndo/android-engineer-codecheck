package jp.co.yumemi.android.code_check.data.repository

import jp.co.yumemi.android.code_check.model.RepositorySummary

interface GithubRepoRepository {
    suspend fun searchRepositories(query: String): List<RepositorySummary>
}