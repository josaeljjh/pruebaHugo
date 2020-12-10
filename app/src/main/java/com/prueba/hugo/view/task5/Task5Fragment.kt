package com.prueba.hugo.view.task5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.prueba.hugo.R
import com.prueba.hugo.data.models.User
import com.prueba.hugo.databinding.FragmentTask5Binding
import com.prueba.hugo.tools.CustomBottomSheetBehavior
import com.prueba.hugo.tools.DynamicBindingAdapter
import com.prueba.hugo.tools.extensions.configureRecyclerBinding
import kotlinx.android.synthetic.main.fragment_task5.*
import kotlinx.android.synthetic.main.sheet_user.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Josaél Hernández on 12/9/20.
 * Contact : josaeljjh@gmail.com
 */
@ExperimentalCoroutinesApi
class Task5Fragment : Fragment() {
    private var task5Binding: FragmentTask5Binding? = null
    private val viewModel by viewModel<Task5ViewModel>()
    lateinit var navController: NavController
    private var optionsAdapter: DynamicBindingAdapter<User>? = null

    private val bottomSheet by lazy {
        CustomBottomSheetBehavior.from(bottom_sheet)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_home, container, false)
        return task5Binding?.root ?: FragmentTask5Binding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            task5 = viewModel
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
        observer()
        initDialogSheet()
    }
    private fun initDialogSheet() {
        viewModel.sheetUser = bottomSheet
        viewModel.init()
    }

    private fun observer() {
        viewModel.userData.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                if(data.isNotEmpty()){
                    updateListOptions(data)
                }
            }

        })
    }

    private fun updateListOptions(list:List<User>) {
        optionsAdapter = viewModel.getAdapterOptions(list)
        optionsAdapter?.let { dynamic ->
            listRecycler?.configureRecyclerBinding(dynamic)
            listRecycler.setHasFixedSize(false)
            listRecycler.adapter?.notifyDataSetChanged()
            listRecycler.scheduleLayoutAnimation()
            dynamic.notifyDataSetChanged()
        }
    }
}
