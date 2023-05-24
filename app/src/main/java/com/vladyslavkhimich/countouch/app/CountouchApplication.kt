package com.vladyslavkhimich.countouch.app

import android.app.Application
import android.content.Context
import android.os.Bundle
import com.vladyslavkhimich.countouch.coordinator.router.Route
import com.vladyslavkhimich.countouch.coordinator.router.Router
import com.vladyslavkhimich.countouch.coordinator.router.RouterConstants
import com.vladyslavkhimich.countouch.ui.CreateActivity
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CountouchApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initializeRouter()
    }

    private fun initializeRouter() {
        val router = Router.getInstance()

        router.addRoute(RouterConstants.CREATE_COUNTER, object: Route {
            override fun route(context: Context, bundle: Bundle) {
                val intent = CreateActivity.newIntent(context)
                context.startActivity(intent)
            }
        })
    }

}