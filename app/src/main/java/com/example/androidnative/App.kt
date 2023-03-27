package com.example.androidnative

import android.app.Application
import android.util.Log


class App : Application() {


    override fun onCreate() {
        super.onCreate()

//        flutterEngine = FlutterEngine(this)
//        flutterEngine.dartExecutor.executeDartEntrypoint(
//            DartExecutor.DartEntrypoint.createDefault())
//
//        FlutterEngineCache
//            .getInstance()
//            .put(FLUTTER_ENGINE_ID, flutterEngine)

        Log.d("Application", "Application: Init")


    }

    companion object {
         const val CHANNEL = "LIVING_NETWORK"
         const val FLUTTER_ENGINE_ID = "flutter_engine"
    }
}