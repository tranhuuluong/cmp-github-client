package io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.tranhuuluong.kmpgithubclient.core.DataStateError
import io.github.tranhuuluong.kmpgithubclient.core.DataStateSuccess
import io.github.tranhuuluong.kmpgithubclient.user.domain.use_case.GetUserListUseCase
import io.github.tranhuuluong.kmpgithubclient.user.domain.use_case.LoadMoreUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserListViewModel(
    getUserListUseCase: GetUserListUseCase,
    private val loadMoreUsersUseCase: LoadMoreUsersUseCase
) : ViewModel() {

    val userListState = getUserListUseCase()
        .map { result -> result.toUiState() }
        .stateIn(viewModelScope, SharingStarted.Lazily, UserListUiState.Empty)

    private val _loadMoreUiState = MutableStateFlow<LoadMoreUiState>(LoadMoreUiState.Idle)
    val loadMoreState = _loadMoreUiState.asStateFlow()

    fun loadMore() {
        viewModelScope.launch {
            if (_loadMoreUiState.value is LoadMoreUiState.Idle) {
                _loadMoreUiState.update { LoadMoreUiState.Loading }
                when (val result = loadMoreUsersUseCase()) {
                    is DataStateSuccess -> _loadMoreUiState.update { LoadMoreUiState.Idle }
                    is DataStateError -> _loadMoreUiState.update {
                        LoadMoreUiState.Error(result.exception.message.orEmpty())
                    }
                }
            }
        }
    }

}