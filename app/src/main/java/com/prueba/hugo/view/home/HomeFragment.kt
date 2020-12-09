package com.prueba.hugo.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.prueba.hugo.databinding.FragmentHomeBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Created by Josaél Hernández on 12/8/20.
 * Contact : josaeljjh@gmail.com
 */

@ExperimentalCoroutinesApi
class HomeFragment : Fragment() {

    private var homeBinding: FragmentHomeBinding? = null
    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_home, container, false)
        return homeBinding?.root ?: FragmentHomeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@HomeFragment
            home = viewModel
        }.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



}


