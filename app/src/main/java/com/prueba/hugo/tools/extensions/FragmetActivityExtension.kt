@file:Suppress("DEPRECATION")

package com.prueba.hugo.tools.extensions

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.view.View.*
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.prueba.hugo.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Josaél Hernández on 12/8/20.
 * Contact : josaeljjh@gmail.com
 */

fun FragmentActivity.launch(action: suspend () -> Unit) {
    lifecycleScope.launch {
        withContext(Dispatchers.Main) {
            action.invoke()
        }
    }
}

fun FragmentActivity.setTransparentStatusBar() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = Color.TRANSPARENT
    }
    darkIcons()
}



fun FragmentActivity.darkIcons() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var flags = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        val currentNightMode = resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)

      /* if(currentNightMode == Configuration.UI_MODE_NIGHT_NO){
            flags = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
               flags = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
           }
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            flags = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR and SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }

        window.decorView.systemUiVisibility = flags
    }
}

//Inline function for starting an Activity
inline fun <reified T : FragmentActivity> FragmentActivity.launchActivity(
    closeCurrent: Boolean = false,
    noinline init: Intent.() -> Unit = {}) {
    val i = Intent(this, T::class.java)
    i.init()
    startActivity(i)
    overridePendingTransition(R.anim.slide_in_right, android.R.anim.fade_out)
    if (closeCurrent) {
        finish()
    }
}
