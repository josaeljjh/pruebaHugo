package com.prueba.hugo.tools.extensions

import androidx.core.content.ContextCompat
import com.prueba.hugo.App

/**
 * Created by Josaél Hernández on 12/11/20.
 * Contact : josaeljjh@gmail.com
 */

fun getColor(res: Int) : Int{
    return  ContextCompat.getColor(App.context, res)
}