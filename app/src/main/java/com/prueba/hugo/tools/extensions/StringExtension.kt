package com.prueba.hugo.tools.extensions

import com.prueba.hugo.App

/**
 * Created by Josaél Hernández on 12/8/20.
 * Contact : josaeljjh@gmail.com
 */

fun getString(res: Int) : String{
    return  App.context.resources.getString(res)
}