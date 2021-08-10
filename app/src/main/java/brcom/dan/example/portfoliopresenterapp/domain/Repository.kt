package brcom.dan.example.portfoliopresenterapp.domain

data class Repository(
    val id: Long,
    val name: String,
    val owner: Owner,
    val stargazersCount: Long,
    val language: String,
    val htmlUrl: String,
    val description: String
)
