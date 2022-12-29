/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.data.repository.GithubRepoRepository
import jp.co.yumemi.android.code_check.model.RepositorySummary
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val repository: GithubRepoRepository,
) : ViewModel() {
    var lastSearchDate: Date? = null
        private set

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _error.value = throwable
    }
    private val _results = MutableLiveData<List<RepositorySummary>>()
    val results: LiveData<List<RepositorySummary>> get() = _results
    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> get() = _error

    fun searchResults(inputText: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            val items = repository.searchRepositories(inputText)
            lastSearchDate = Date()
            withContext(Dispatchers.Main) {
                _results.value = items
            }
        }
    }
}