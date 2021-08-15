package brcom.dan.example.portfoliopresenterapp.domain

import com.google.gson.annotations.SerializedName

data class Repository(
    val id: Long,
    val name: String,
    val owner: Owner,
    @SerializedName("stargazers_count") val stargazersCount: Long,
    val language: String,
    @SerializedName("html_url") val htmlUrl: String,
    val description: String
)
