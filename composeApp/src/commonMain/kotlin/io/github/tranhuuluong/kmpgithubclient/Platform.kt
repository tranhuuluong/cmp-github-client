package io.github.tranhuuluong.kmpgithubclient

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform