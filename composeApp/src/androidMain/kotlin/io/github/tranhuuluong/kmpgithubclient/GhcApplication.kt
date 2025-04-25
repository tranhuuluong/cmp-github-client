package io.github.tranhuuluong.kmpgithubclient

import android.app.Application
import io.github.tranhuuluong.kmpgithubclient.di.initKoin
import org.koin.android.ext.koin.androidContext

class GhcApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@GhcApplication)
        }
    }
}