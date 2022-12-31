package jp.co.yumemi.android.codecheck.data.repository.impl

import jp.co.yumemi.android.codecheck.data.ErrorResult
import jp.co.yumemi.android.codecheck.data.repository.GithubRepoRepository
import jp.co.yumemi.android.codecheck.model.RepositorySummary

class FakeGithubRepoRepositoryImpl : GithubRepoRepository {
    val successData = buildList<RepositorySummary> {
        repeat(10) {
            RepositorySummary(
                name = "flutter: $it",
                ownerIconUrl = "https://avatars.githubusercontent.com/u/14101776?v=4",
                stargazersCount = 147731,
                forksCount = 24075,
                openIssuesCount = 11390,
                watchersCount = 3561,
                language = "Dart",
            )
        }
    }
    var isSuccess = true
    val errorData = ErrorResult.NetworkError

    override suspend fun searchRepositories(query: String): List<RepositorySummary> {
        return if (isSuccess) {
            successData
        } else {
            throw errorData
        }
    }
}