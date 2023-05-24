package com.vladyslavkhimich.countouch.coordinator

import android.content.Context
import androidx.core.os.bundleOf
import com.vladyslavkhimich.countouch.coordinator.router.Router
import com.vladyslavkhimich.countouch.coordinator.router.RouterConstants
import javax.inject.Inject

interface MainCoordination {
    fun navigateToCreateCounter(context: Context)

    fun navigateToEditCounter(context: Context, counterId: Long)
}

class MainCoordinator @Inject constructor() : MainCoordination {
    override fun navigateToCreateCounter(context: Context) {
        Router.getInstance().navigateTo(RouterConstants.CREATE_COUNTER, context, bundleOf())
    }

    override fun navigateToEditCounter(context: Context, counterId: Long) {

    }
}