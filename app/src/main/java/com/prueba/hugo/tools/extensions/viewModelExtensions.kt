package com.prueba.hugo.tools.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Josaél Hernández on 12/9/20.
 * Contact : josaeljjh@gmail.com
 */

fun ViewModel.launch(action: suspend () -> Unit) {
    viewModelScope.launch {
        withContext(Dispatchers.Main) {
            action.invoke()
        }
    }
}

fun ViewModel.launchAPIRequest(action: suspend () -> Unit) {
    viewModelScope.launch {
        withContext(Dispatchers.IO) {
            action.invoke()
        }
    }
}