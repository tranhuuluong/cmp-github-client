@file:OptIn(ExperimentalCoroutinesApi::class)

package io.github.tranhuuluong.kmpgithubclient.user.presentation.user_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import io.github.tranhuuluong.kmpgithubclient.core.util.TimeProvider
import io.github.tranhuuluong.kmpgithubclient.user.domain.use_case.GetUserDetailUseCase
import io.github.tranhuuluong.kmpgithubclient.user.presentation.navigation.Route
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val userId: String,
    getUserDetailUseCase: GetUserDetailUseCase,
    timeProvider: TimeProvider,
) : ViewModel() {

    constructor(
        savedStateHandle: SavedStateHandle,
        getUserDetailUseCase: GetUserDetailUseCase,
        timeProvider: TimeProvider
    ) : this(
        userId = savedStateHandle.toRoute<Route.UserDetail>().id,
        getUserDetailUseCase = getUserDetailUseCase,
        timeProvider = timeProvider
    )

    private val fetchUserDetailTrigger = Channel<String>()
    val userDetailUiState = merge(flowOf(userId), fetchUserDetailTrigger.receiveAsFlow())
        .flatMapLatest { getUserDetailUseCase(it) }
        .map { it.toUiState(timeProvider.systemDefaultTimezone()) }
        .stateIn(viewModelScope, SharingStarted.Lazily, UserDetailUiState.Loading)

    fun retry() {
        viewModelScope.launch {
            fetchUserDetailTrigger.send(userId)
        }
    }
}
