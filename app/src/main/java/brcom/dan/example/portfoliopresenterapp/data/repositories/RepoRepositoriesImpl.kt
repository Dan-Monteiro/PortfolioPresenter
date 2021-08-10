package brcom.dan.example.portfoliopresenterapp.data.repositories

import brcom.dan.example.portfoliopresenterapp.data.services.GitHubService
import brcom.dan.example.portfoliopresenterapp.domain.Repository
import kotlinx.coroutines.flow.flow

class RepoRepositoriesImpl(private val service: GitHubService): RepoRepository {
    override suspend fun listRepositories(user: String) = flow<List<Repository>> {
        val result = service.listRepos(user)
        emit(result)
    }
}