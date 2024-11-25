package org.pandatech.bookpedia

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.pandatech.bookpedia.di.initKoin

class BookApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BookApplication)
        }
    }
}