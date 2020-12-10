package com.prueba.hugo.view.home


import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.prueba.hugo.App
import com.prueba.hugo.R
import kotlinx.coroutines.ExperimentalCoroutinesApi


/**
 * Created by Josaél Hernández on 12/8/20.
 * Contact : josaeljjh@gmail.com
 */

@ExperimentalCoroutinesApi
class HomeViewModel() : ViewModel() {

    lateinit var navViewModel: NavController

    fun onClick() {
        Toast.makeText(App.context, "Proximamente", Toast.LENGTH_SHORT).show()
    }

    fun onClickTask1() {
        navViewModel.navigate(R.id.action_task1)
    }
    fun onClickTask2() {
        navViewModel.navigate(R.id.action_task2)
    }
    fun onClickTask3() {
        navViewModel.navigate(R.id.action_task3)
    }
    fun onClickTask4() {
        navViewModel.navigate(R.id.action_task4)
    }
    fun onClickTask5() {
        navViewModel.navigate(R.id.action_task5)
    }
}

