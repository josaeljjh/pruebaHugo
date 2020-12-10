package com.prueba.hugo.view.task2


import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.Bindable
import androidx.databinding.adapters.TextViewBindingAdapter.setPassword
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.prueba.hugo.R
import com.prueba.hugo.tools.SingleLiveEvent
import com.prueba.hugo.tools.extensions.afterTextChanged
import com.prueba.hugo.tools.extensions.getString
import kotlinx.coroutines.ExperimentalCoroutinesApi


/**
 * Created by Josaél Hernández on 12/8/20.
 * Contact : josaeljjh@gmail.com
 */

@ExperimentalCoroutinesApi
class Task2ViewModel() : ViewModel() {

    var showCelsius = SingleLiveEvent<Boolean>()
    var showFahrenheit = SingleLiveEvent<Boolean>()
    var textCelsius = MutableLiveData<String>()
    var textFahrenheit = MutableLiveData<String>()
    var celsius = MutableLiveData<String>()
    var fahrenheit = MutableLiveData<String>()
    var hintCelsius = MutableLiveData<String>()
    var hintFahrenheit = MutableLiveData<String>()

    fun init(){
        showCelsius.postValue(false)
        showFahrenheit.postValue(false)
        hintCelsius.value = getString(R.string.cel)
        hintFahrenheit.value = getString(R.string.fah)
    }

    fun afterTextChangedC(editable: Editable){
        showCelsius.postValue(false)
        showFahrenheit.postValue(true)
        hintFahrenheit.value = ""
        val celsius:String = editable.toString()
        if(celsius.isNotEmpty()){
            val operacion: Int = (celsius.toInt() * (9 / 5.0) + 32).toInt()
            val valor:String = operacion.toString()
            textFahrenheit.value = "$valor °F"
        }else{
            textFahrenheit.value = ""
        }
    }

    fun afterTextChangedF(editable: Editable){
        showCelsius.postValue(true)
        showFahrenheit.postValue(false)
        hintCelsius.value = ""
        val fahrenheit:String = editable.toString()
        if(fahrenheit.isNotEmpty()) {
            val operacion: Int = (((fahrenheit.toInt() - 32) * 5) / 9).toInt()
            val valor: String = operacion.toString()
            textCelsius.value = "$valor °C"
            textCelsius.value = "$valor ${getString(R.string.fah)}"
        }else{
            textCelsius.value = ""
        }
    }

    fun onClick() {
        showCelsius.postValue(false)
        showFahrenheit.postValue(false)
        celsius.value = ""
        fahrenheit.value = ""
        hintCelsius.value = getString(R.string.cel)
        hintFahrenheit.value = getString(R.string.fah)
    }

}

