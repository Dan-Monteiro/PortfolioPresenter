package brcom.dan.example.portfoliopresenterapp.domain

import brcom.dan.example.portfoliopresenterapp.core.UseCase
import brcom.dan.example.portfoliopresenterapp.data.repositories.RepoRepository
import kotlinx.coroutines.flow.Flow

class ListUserRepositoriesUseCase(
    private val repository: RepoRepository
    ) : UseCase<String, List<Repository>>() {
    override suspend fun execute(param: String): Flow<List<Repository>> {
        return repository.listRepositories(param)
    }
}