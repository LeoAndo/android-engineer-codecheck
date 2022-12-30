package jp.co.yumemi.android.codecheck.data.repository

import jp.co.yumemi.android.codecheck.model.RepositorySummary

interface GithubRepoRepository {
    suspend fun searchRepositories(query: String): List<RepositorySummary>
}