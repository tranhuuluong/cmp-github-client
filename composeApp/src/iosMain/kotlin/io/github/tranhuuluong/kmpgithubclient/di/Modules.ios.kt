package io.github.tranhuuluong.kmpgithubclient.di

import io.github.tranhuuluong.kmpgithubclient.user.data.local.DatabaseBuilderFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
        single { DatabaseBuilderFactory() }
    }