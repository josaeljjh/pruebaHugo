package com.prueba.hugo.di.module

import com.prueba.hugo.data.db.room.PersonDB
import com.prueba.hugo.data.db.room.RoomDB
import com.prueba.hugo.domain.repository.Repository
import com.prueba.hugo.domain.user.UseCaseUser
import com.prueba.hugo.view.home.HomeViewModel
import com.prueba.hugo.view.task1.Task1ViewModel
import com.prueba.hugo.view.task2.Task2ViewModel
import com.prueba.hugo.view.task3.Task3ViewModel
import com.prueba.hugo.view.task4.Task4ViewModel
import com.prueba.hugo.view.task5.Task5ViewModel
import com.prueba.hugo.view.task6.Task6ViewModel
import com.prueba.hugo.view.task7.Task7ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


/**
 * Created by Josaél Hernández on 12/8/20.
 * Contact : josaeljjh@gmail.com
 */

@ExperimentalCoroutinesApi
object InyectModule {
    val inyectModule: Module = module {
        single { RoomDB() }
        single { Repository(get()) }
        single { UseCaseUser(get()) }
        viewModel { HomeViewModel() }
        viewModel { Task1ViewModel() }
        viewModel { Task2ViewModel() }
        viewModel { Task3ViewModel() }
        viewModel { Task4ViewModel() }
        viewModel { Task5ViewModel(get()) }
        viewModel { Task6ViewModel() }
        viewModel { Task7ViewModel(get()) }
    }
}