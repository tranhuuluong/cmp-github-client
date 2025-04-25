package io.github.tranhuuluong.kmpgithubclient.user.presentation.user_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import io.github.tranhuuluong.kmpgithubclient.user.domain.use_case.GetUserDetailUseCase
import io.github.tranhuuluong.kmpgithubclient.user.presentation.navigation.Route
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class UserDetailViewModel(
    savedStateHandle: SavedStateHandle,
    getUserDetailUseCase: GetUserDetailUseCase
) : ViewModel() {

    private val userId = savedStateHandle.toRoute<Route.UserDetail>().id

    val userDetailUiState: StateFlow<UserDetailUiState> = getUserDetailUseCase(userId)
        .map { it.toUiState() }
        .stateIn(viewModelScope, SharingStarted.Lazily, UserDetailUiState.Loading)
}