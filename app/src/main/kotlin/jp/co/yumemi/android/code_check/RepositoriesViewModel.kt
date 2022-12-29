/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.model.Item
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class RepositoriesViewModel : ViewModel() {
    var lastSearchDate: Date? = null
        private set

    private val coroutineExceptionHandler = CoroutineExceptionHandler() { _, throwable ->
        _error.value = throwable
    }
    private val _results = MutableLiveData<List<Item>>()
    val results get() = _results
    private val _error = MutableLiveData<Throwable>()
    val error get() = _error

    fun searchResults(inputText: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            withContext(Dispatchers.IO) {
                val response: HttpResponse =
                    HttpClient(Android).get("https://api.github.com/search/repositories") {
                        header("Accept", "application/vnd.github.v3+json")
                        parameter("q", inputText)
                    }

                val jsonBody = JSONObject(response.receive<String>())

                val jsonItems = jsonBody.optJSONArray("items") ?: JSONArray()

                val items = mutableListOf<Item>()

                for (i in 0 until jsonItems.length()) {
                    val jsonItem = jsonItems.optJSONObject(i)
                    val name = jsonItem.optString("full_name")
                    val ownerIconUrl =
                        jsonItem.optJSONObject("owner")?.optString("avatar_url") ?: ""
                    val language = jsonItem.optString("language")
                    val stargazersCount = jsonItem.optLong("stargazers_count")
                    val watchersCount = jsonItem.optLong("watchers_count")
                    val forksCount = jsonItem.optLong("forks_count")
                    val openIssuesCount = jsonItem.optLong("open_issues_count")

                    items.add(
                        Item(
                            name = name,
                            ownerIconUrl = ownerIconUrl,
                            language = language,
                            stargazersCount = stargazersCount,
                            watchersCount = watchersCount,
                            forksCount = forksCount,
                            openIssuesCount = openIssuesCount
                        )
                    )
                }
                lastSearchDate = Date()
                withContext(Dispatchers.Main) {
                    _results.value = items
                }
            }
        }
    }
}