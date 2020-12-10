package com.prueba.hugo.view.host

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.prueba.hugo.R
import com.prueba.hugo.tools.extensions.marginUpdate
import com.prueba.hugo.tools.extensions.setTransparentStatusBar
import kotlinx.android.synthetic.main.activity_host.*

/**
 * Created by Josaél Hernández on 12/8/20.
 * Contact : josaeljjh@gmail.com
 */

class HostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)
        config()
    }

    private fun config() {
        setTransparentStatusBar()
        masterHost.marginUpdate()
    }

}