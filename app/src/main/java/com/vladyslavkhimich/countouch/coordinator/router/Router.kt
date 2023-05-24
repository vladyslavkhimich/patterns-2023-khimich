package com.vladyslavkhimich.countouch.coordinator.router

import android.content.Context
import android.content.Intent
import android.os.Bundle
import java.lang.IllegalArgumentException

class Router {

    companion object {
        private var instance: Router? = null

        fun getInstance(): Router {
            return if (instance == null) {
                instance = Router()
                instance!!
            } else {
                instance!!
            }
        }
    }

    private val routes: MutableMap<String, Route> = HashMap()

    private val activityCreator: MutableMap<String, ActivityCreator> = HashMap()

    fun addRoute(name: String, route: Route) {
        routes[name] = route
    }

    fun navigateTo(name: String, context: Context, args: Bundle) {
        if (routes.containsKey(name)) {
            routes[name]?.route(context, args)
        } else {
            throw IllegalArgumentException("No route found for: $name")
        }
    }
}

interface Route {
    fun route(context: Context, bundle: Bundle)
}

interface ActivityCreator {
    fun getActivity(context: Context, bundle: Bundle): Intent
}