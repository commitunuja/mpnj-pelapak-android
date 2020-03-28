package id.poter.absen.server

import android.os.StrictMode
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

class app : MultiDexApplication() {

    companion object {
        @get:Synchronized
        lateinit var instance: app
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        MultiDex.install(this)
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

}