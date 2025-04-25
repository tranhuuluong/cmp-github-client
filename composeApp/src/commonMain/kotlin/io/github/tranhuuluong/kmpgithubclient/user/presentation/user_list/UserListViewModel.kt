package io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.tranhuuluong.kmpgithubclient.user.domain.use_case.GetUserListUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class UserListViewModel(
    getUserListUseCase: GetUserListUseCase
) : ViewModel() {

    val userListState = getUserListUseCase()
        .map { result -> result.toUiState() }
        .stateIn(viewModelScope, SharingStarted.Lazily, UserListUiState.Empty)
}