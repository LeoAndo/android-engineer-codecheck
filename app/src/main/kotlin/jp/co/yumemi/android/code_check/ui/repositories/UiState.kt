package jp.co.yumemi.android.code_check.ui.repositories

import jp.co.yumemi.android.code_check.model.RepositorySummary

sealed interface UiState {
    object Initial : UiState
    object Loading : UiState
    data class Data(val repositories: List<RepositorySummary>) : UiState
    data class Error(val throwable: Throwable) : UiState
}