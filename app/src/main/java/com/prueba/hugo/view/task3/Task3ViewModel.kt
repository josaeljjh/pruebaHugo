package com.prueba.hugo.view.task3

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prueba.hugo.App
import com.prueba.hugo.R
import com.prueba.hugo.tools.extensions.getString
import java.text.SimpleDateFormat

/**
 * Created by Josaél Hernández on 12/9/20.
 * Contact : josaeljjh@gmail.com
 */

class Task3ViewModel : ViewModel() {

    val options = arrayListOf("vuelo de ida","vuelo ida/retorno")
    val showDialogOptions = MutableLiveData<Boolean>()
    val showDialogDate = MutableLiveData<Int>()
    val option = MutableLiveData<String>()
    val datetoGo = MutableLiveData<String>()
    val dateReturn = MutableLiveData<String>()
    val enableOptions = MutableLiveData<Boolean>()
    val enableBtn = MutableLiveData<Boolean>()
    val visibleReturn = MutableLiveData<Boolean>()
    val showSucess = MutableLiveData<Boolean>()
    val errorDate = MutableLiveData<Boolean>()

    init {
        showDialogOptions.value = false
        option.value = getString(R.string.text_method_fly)
        showDialogDate.value = -1
        enableOptions.value = false
        visibleReturn.value = true
        enableBtn.value = false
        errorDate.value = false
    }

    fun showOptions(){
        showDialogOptions.value = true
    }

    fun showDialogDate(isReturn:Boolean){
        if(enableOptions.value!!){
            if(isReturn){
                showDialogDate.value = 1
            }else{
                showDialogDate.value = 0
            }
        }else{
            Toast.makeText(App.context,"Debes de seleccionar una opción de viaje", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun checkData(){
        enableBtn.value = false
        if(option.value == "vuelo de ida" ){
            if(!datetoGo.value.isNullOrEmpty()){
                enableBtn.value = true
            }
        }else{
            if(!datetoGo.value.isNullOrEmpty() && !dateReturn.value.isNullOrEmpty()){
                val dateFormat =  SimpleDateFormat("dd/MM/yyyy")
                val  dateGo = dateFormat.parse(datetoGo.value!!)
                val  dateRetn = dateFormat.parse(dateReturn.value!!)

                if(dateRetn!!.before(dateGo)){
                    errorDate.value = true
                    dateReturn.value = App.context.getString(R.string.hint_out_date)
                }else{
                    enableBtn.value = true
                }
            }
        }
    }

    fun showSucess(){
        showSucess.value = true
    }
}