package com.prueba.hugo.view.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.prueba.hugo.R
import com.prueba.hugo.tools.extensions.launch
import com.prueba.hugo.tools.extensions.launchActivity
import com.prueba.hugo.view.host.HostActivity
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

/**
 * Created by Josaél Hernández on 12/8/20.
 * Contact : josaeljjh@gmail.com
 */

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        gotoHome()
    }

    private fun gotoHome(){
        launch {
            delay(TimeUnit.SECONDS.toMillis(3))
            launchActivity<HostActivity>(true)
        }
    }
}