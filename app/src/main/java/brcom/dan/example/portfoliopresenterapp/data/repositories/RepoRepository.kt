package brcom.dan.example.portfoliopresenterapp.data.repositories

import brcom.dan.example.portfoliopresenterapp.domain.Repository
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    suspend fun listRepositories(user: String): Flow<List<Repository>>
}