package com.prueba.hugo

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.prueba.hugo.di.module.InyectModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by Josaél Hernández on 12/8/20.
 * Contact : josaeljjh@gmail.com
 */

@ExperimentalCoroutinesApi
class App: Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: App private set
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = instance.applicationContext
        startKoin {
            androidLogger()
            androidContext(this@App)
            // load modules here
            modules(InyectModule.inyectModule)
        }
    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}