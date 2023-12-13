package com.project.north_south

import android.app.Application
import com.project.north_south.subAlgorithms.Storage

class MainApplication: Application() {

    override fun onTerminate() {
        super.onTerminate()
        Storage(this).clearCurrentData()
    }
}