package com.prueba.hugo.view.task3

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.prueba.hugo.R
import com.prueba.hugo.databinding.FragmentTask3Binding
import com.prueba.hugo.tools.extensions.getCalendarFromDateTravel
import com.prueba.hugo.tools.extensions.showAlert
import com.prueba.hugo.tools.extensions.showOptionDialog
import com.prueba.hugo.view.task1.Task1ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Josaél Hernández on 12/9/20.
 * Contact : josaeljjh@gmail.com
 */

class Task3Fragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private var task3Binding: FragmentTask3Binding? = null
    private val viewModel by viewModel<Task3ViewModel>()
    lateinit var navController: NavController
    private var dialogCalendar: DatePickerDialog? = null
    private var typeDate = -1

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_home, container, false)
        return task3Binding?.root ?: FragmentTask3Binding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            task3 = viewModel
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

        dialogOptionsfly()
        dialogDate()
        dialogSuccess()
        dialogError()
    }

    fun dialogOptionsfly() {
        viewModel.showDialogOptions.observe(viewLifecycleOwner, {
            if (it) {
                activity?.showOptionDialog(requireContext(), viewModel.options, fun(value) {
                    viewModel.option.value = value
                    viewModel.enableOptions.value = true
                    viewModel.visibleReturn.value = value == "vuelo ida/retorno"
                    viewModel.checkData()
                })
                viewModel.showDialogOptions.value = false
            }
        })

    }

    fun dialogSuccess() {
        viewModel.showSucess.observe(viewLifecycleOwner, {
            if (it) {
                requireActivity().showAlert(
                        "Se ha reservado un ${viewModel.option.value} para la fecha ${viewModel.datetoGo.value} \uD83D\uDEEC \uD83D\uDEEC",
                        getString(R.string.text_title_dialog_travel)
                )
                viewModel.showSucess.value = false
            }
        })
    }

    fun dialogError() {
        viewModel.errorDate.observe(viewLifecycleOwner, {
            if (it) {
                requireActivity().showAlert(
                        getString(R.string.text_error_message_travel),
                        getString(R.string.text_title_dialog_travel)
                )
                viewModel.errorDate.value = false
            }
        })
    }

    fun dialogDate() {
        viewModel.showDialogDate.observe(viewLifecycleOwner, {
            if (it != -1) {
                typeDate = it
                dialogCalendar()
                viewModel.showDialogDate.value = -1
            }
        })
    }

    private fun dialogCalendar() {
        dialogCalendar = requireActivity().getCalendarFromDateTravel(this)
        dialogCalendar?.let {
            it.show()
            it.setOnDismissListener {

            }
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val DateString =
                "${String.format("%02d", dayOfMonth)}/${String.format("%02d", month + 1)}/$year"
        when (typeDate) {
            0 -> {
                viewModel.datetoGo.value = DateString
            }
            1 -> {
                viewModel.dateReturn.value = DateString
            }
        }
        viewModel.checkData()
    }
}