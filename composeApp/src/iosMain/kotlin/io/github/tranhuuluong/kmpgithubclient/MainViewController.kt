package io.github.tranhuuluong.kmpgithubclient

import androidx.compose.ui.window.ComposeUIViewController
import io.github.tranhuuluong.kmpgithubclient.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {
    App()
}