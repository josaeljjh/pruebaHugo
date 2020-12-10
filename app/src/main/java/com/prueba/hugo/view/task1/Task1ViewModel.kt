package com.prueba.hugo.view.task1

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Josaél Hernández on 12/8/20.
 * Contact : josaeljjh@gmail.com
 */

class Task1ViewModel: ViewModel() {

    val count = MutableLiveData<Int>()
    val backstack = MutableLiveData<Boolean>()

    var ct = 0

    init {
        count.value = ct
        backstack.value = false
    }

    fun upgradeCount(){
        ct += 1
        count.value = ct
    }

    fun popback(){
        backstack.value = true
    }

}