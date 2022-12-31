package jp.co.yumemi.android.codecheck.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepositorySummary(
    val name: String,
    val ownerIconUrl: String,
    val language: String,
    val stargazersCount: Int,
    val watchersCount: Int,
    val forksCount: Int,
    val openIssuesCount: Int,
) : Parcelable