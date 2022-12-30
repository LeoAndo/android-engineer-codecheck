package jp.co.yumemi.android.code_check.data.api.response

@kotlinx.serialization.Serializable
data class GithubErrorResponse(val documentation_url: String, val message: String)