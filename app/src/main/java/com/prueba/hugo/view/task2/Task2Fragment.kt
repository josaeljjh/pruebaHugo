package com.prueba.hugo.view.task2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.prueba.hugo.R
import com.prueba.hugo.databinding.FragmentHomeBinding
import com.prueba.hugo.databinding.FragmentTask2Binding
import kotlinx.android.synthetic.main.fragment_task2.*
import kotlinx.android.synthetic.main.fragment_task4.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Created by Josaél Hernández on 12/8/20.
 * Contact : josaeljjh@gmail.com
 */

@ExperimentalCoroutinesApi
class Task2Fragment : Fragment() {

    private var task2Binding: FragmentTask2Binding? = null
    private val viewModel by viewModel<Task2ViewModel>()
    lateinit var navController: NavController

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_home, container, false)
        return task2Binding?.root ?: FragmentTask2Binding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            task2 = viewModel
        }.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle the back button event
                navController.navigate(R.id.action_home_back)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        viewModel.init()
    }

}


