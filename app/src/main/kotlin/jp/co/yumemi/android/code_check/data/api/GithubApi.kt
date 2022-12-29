package jp.co.yumemi.android.code_check.data.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import jp.co.yumemi.android.code_check.BuildConfig
import jp.co.yumemi.android.code_check.data.ErrorResult
import jp.co.yumemi.android.code_check.data.api.response.GithubSearchResponse
import jp.co.yumemi.android.code_check.data.api.response.toModel
import jp.co.yumemi.android.code_check.model.RepositorySummary
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class GithubApi {
    private val format by lazy {
        Json { ignoreUnknownKeys = true }
    }
    private val httpClient: HttpClient by lazy {
        HttpClient(Android) {
            defaultRequest {
                url.takeFrom(URLBuilder().takeFrom(BuildConfig.GITHUB_API_DOMAIN).apply {
                    encodedPath += url.encodedPath
                })
                header("Accept", "application/vnd.github.v3+json")
                header("Authorization", "Bearer ${BuildConfig.GITHUB_ACCESS_TOKEN}")
                header("X-GitHub-Api-Version", "2022-11-28")
            }
            install(HttpTimeout) {
                requestTimeoutMillis = TIMEOUT_MILLIS
                connectTimeoutMillis = TIMEOUT_MILLIS
                socketTimeoutMillis = TIMEOUT_MILLIS
            }
            HttpResponseValidator {
                validateResponse { response ->
                    when (response.status) {
                        HttpStatusCode.Unauthorized -> {
                            throw ErrorResult.UnAuthorizedError
                        }
                        HttpStatusCode.NotFound -> {
                            throw ErrorResult.NotFoundError
                        }
                        HttpStatusCode.Forbidden -> {
                            throw ErrorResult.ForbiddenError
                        }
                    }
                }
            }
        }
    }

    suspend fun searchRepositories(query: String): List<RepositorySummary> {
        val response: HttpResponse = httpClient.get {
            url { path("search", "repositories") }
            parameter("q", query)
        }
        val data =
            format.decodeFromString<GithubSearchResponse>(response.receive())
        return data.toModel()
    }

    companion object {
        private const val TIMEOUT_MILLIS: Long = 30 * 1000
    }
}