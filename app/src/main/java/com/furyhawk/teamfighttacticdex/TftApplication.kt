package com.furyhawk.teamfighttacticdex

import android.app.Application
import com.furyhawk.teamfighttacticdex.data.AppContainer
import com.furyhawk.teamfighttacticdex.data.AppContainerImpl

class TftApplication : Application()  {
    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}