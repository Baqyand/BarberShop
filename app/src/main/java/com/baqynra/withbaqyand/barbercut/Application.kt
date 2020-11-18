package com.baqynra.withbaqyand.barbercut

import android.app.Application
import com.mazenrashed.printooth.Printooth

class Application : Application(){
    override fun onCreate() {
        super.onCreate()
        Printooth.init(this)
    }
}