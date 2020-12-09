package com.prueba.hugo.di.module

import com.prueba.hugo.view.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
        viewModel { HomeViewModel() }
    }
}