package io.github.tranhuuluong.kmpgithubclient.di

import io.github.tranhuuluong.kmpgithubclient.user.data.OfflineFirstUserRepository
import io.github.tranhuuluong.kmpgithubclient.user.domain.UserRepository
import io.github.tranhuuluong.kmpgithubclient.user.domain.use_case.GetUserListUseCase
import io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list.UserListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    singleOf(::OfflineFirstUserRepository).bind<UserRepository>()
    factoryOf(::GetUserListUseCase)
    viewModelOf(::UserListViewModel)
}