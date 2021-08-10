package brcom.dan.example.portfoliopresenterapp.data.services

import brcom.dan.example.portfoliopresenterapp.domain.Repository
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("user/{user}/repos")
    suspend fun listRepos(@Path("user") user: String): List<Repository>
}
