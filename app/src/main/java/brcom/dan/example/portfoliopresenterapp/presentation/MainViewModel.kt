package brcom.dan.example.portfoliopresenterapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import brcom.dan.example.portfoliopresenterapp.domain.ListUserRepositoriesUseCase
import brcom.dan.example.portfoliopresenterapp.domain.Repository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(
    private val listUserRepositoriesUseCase: ListUserRepositoriesUseCase)
    : ViewModel(){

    private val _repos = MutableLiveData<State>()
    val repos: LiveData<State> = _repos

    fun getRepoList(user: String) {
        viewModelScope.launch {
            listUserRepositoriesUseCase
                .execute(user)
                .onStart {
                    _repos.postValue(State.Loadind)
                }
                .catch {
                    _repos.postValue(State.Error(it))
                }
                .collect {
                    _repos.postValue(State.Success(it))
                }
        }
    }

    fun checkReposVisibility(list: List<Repository>): Boolean {
        return list.isEmpty()
    }

    sealed class State {
        object Loadind: State()
        data class Success(val list: List<Repository>) : State()
        data class Error(val error: Throwable): State()
    }
}