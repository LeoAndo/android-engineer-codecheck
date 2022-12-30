package jp.co.yumemi.android.code_check.data

import io.ktor.client.features.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception

sealed class SafeResult<out R> {
    data class Success<out T>(val data: T) : SafeResult<T>()
    data class Error(val errorResult: ErrorResult) : SafeResult<Nothing>()
}

sealed class ErrorResult : Exception() {
    object UnAuthorizedError : ErrorResult()
    object NotFoundError : ErrorResult()
    object ForbiddenError : ErrorResult()
    object NetworkError : ErrorResult()
    data class UnexpectedError(override val message: String) : ErrorResult()
}

suspend fun <T> dataOrThrow(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): T {
    return withContext(dispatcher) {
        return@withContext when (val result = safeCall(dispatcher) { apiCall.invoke() }) {
            is SafeResult.Error -> throw  result.errorResult
            is SafeResult.Success -> result.data
        }
    }
}

private suspend fun <T> safeCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): SafeResult<T> {
    return withContext(dispatcher) {
        try {
            SafeResult.Success(apiCall.invoke())
        } catch (e: Throwable) {
            when (e) {
                is HttpRequestTimeoutException, is ConnectTimeoutException, is SocketTimeoutException -> {
                    SafeResult.Error(ErrorResult.NetworkError)
                }
                else -> {
                    SafeResult.Error(
                        ErrorResult.UnexpectedError(
                            e.localizedMessage ?: "UnexpectedError"
                        )
                    )
                }
            }
        }
    }
}