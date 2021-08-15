package brcom.dan.example.portfoliopresenterapp.data.repositories

import brcom.dan.example.portfoliopresenterapp.core.RemoteException
import brcom.dan.example.portfoliopresenterapp.data.services.GitHubService
import brcom.dan.example.portfoliopresenterapp.domain.Repository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class RepoRepositoriesImpl(private val service: GitHubService): RepoRepository {
    override suspend fun listRepositories(user: String) = flow<List<Repository>> {
        try {
            val result = service.listRepos(user)
            emit(result)
        }catch (ex: HttpException){
            if(ex.code() == 404){
                throw RemoteException("Usuário não encontrado!")
            }else{
                throw RemoteException(ex.message ?: "Não foi possível realizar a busca no momento!")
            }
        }
    }
}