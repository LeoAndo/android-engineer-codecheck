/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.data.repository.GithubRepoRepository
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val repository: GithubRepoRepository,
) : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _uiState.value = UiState.Error(throwable)
    }
    private val _uiState = MutableLiveData<UiState>(UiState.Initial)
    val uiState: LiveData<UiState> get() = _uiState

    fun searchResults(inputText: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            _uiState.value = UiState.Loading
            val items = repository.searchRepositories(inputText)
            withContext(Dispatchers.Main) {
                _uiState.value = UiState.Data(items)
            }
        }
    }
}