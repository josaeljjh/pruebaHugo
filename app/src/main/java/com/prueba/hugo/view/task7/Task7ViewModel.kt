package com.prueba.hugo.view.task7

import android.text.Editable
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.prueba.hugo.App.Companion.context
import com.prueba.hugo.R
import com.prueba.hugo.data.db.room.Person
import com.prueba.hugo.data.models.User
import com.prueba.hugo.data.models.UserModel
import com.prueba.hugo.databinding.ItemList2Binding
import com.prueba.hugo.domain.user.UseCaseUser
import com.prueba.hugo.tools.CustomBottomSheetBehavior
import com.prueba.hugo.tools.DynamicBindingAdapter
import com.prueba.hugo.tools.extensions.expandedDialog
import com.prueba.hugo.tools.extensions.hideKeyboard
import com.prueba.hugo.tools.extensions.launchAPIRequest
import com.prueba.hugo.tools.extensions.setSafeOnClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlin.collections.ArrayList


/**
 * Created by Josaél Hernández on 12/9/20.
 * Contact : josaeljjh@gmail.com
 */

@ExperimentalCoroutinesApi
class Task7ViewModel(private val case: UseCaseUser) : ViewModel() {

    var userData = MutableLiveData<List<com.prueba.hugo.data.db.room.Person>>()
    var datosModel:ArrayList<User> = ArrayList()

    var txtTitle = MutableLiveData<String>()
    var txtName = MutableLiveData<String>()
    var txtLast = MutableLiveData<String>()

    var showCreate = MutableLiveData<Boolean>()
    var showDelete = MutableLiveData<Boolean>()
    var showUpdate = MutableLiveData<Boolean>()

    var IdSelected:Int = 0

    //bottom sheet
    var sheetUser: CustomBottomSheetBehavior<LinearLayout>? = null
    var master: CoordinatorLayout? = null

    private val searchChanel = ConflatedBroadcastChannel<String>()

    fun init() {
        sheetUser?.isHideable = true
        sheetUser expandedDialog false
        txtTitle.value = "Datos"
        showDelete.postValue(false)
        showUpdate.postValue(false)
        showCreate.postValue(true)

        LoadData()
    }

    private fun LoadData() {
        launchAPIRequest {
            try {
                case.getDataPerson().collect { datos ->
                    if (datos.isEmpty()) {
                        //serealizar datos
                        val jsonString =
                            context.resources.openRawResource(R.raw.users).bufferedReader()
                                .use { it.readText() }
                        val data = Gson().fromJson(jsonString, UserModel::class.java)
                        createBD(data.user as ArrayList<User>)
                    } else {
                        userData.postValue(datos)
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun createBD(data: ArrayList<User>) {
        launchAPIRequest {
            if (data.isNotEmpty()) {
                data.forEach { user ->
                    val person = com.prueba.hugo.data.db.room.Person(
                        user.id.toInt(),user.name, user.last_name
                    )
                    try {
                        case.insertDataPerson(person)
                    } catch (e: Exception) {
                    }
                }
               Load()
            }
        }
    }

    fun Load(){
        launchAPIRequest {
            case.getDataPerson().collect { datos ->
                if (datos.isNotEmpty()) {
                    userData.postValue(datos)
                }
            }
        }
    }

    fun getAdapterOptions(list:List<com.prueba.hugo.data.db.room.Person>): DynamicBindingAdapter<com.prueba.hugo.data.db.room.Person>? {
        var adapter: DynamicBindingAdapter<com.prueba.hugo.data.db.room.Person>? = null
        try {
            adapter = DynamicBindingAdapter(
                R.layout.item_list2,
                list,
                fun(vh, view, data, _) {
                    view as ItemList2Binding
                    view.list = data
                    vh.itemView.setSafeOnClickListener {
                        UpdateDelete(data)
                    }
                })
        } catch (e: Exception) { }
        return adapter!!
    }

    fun afterTextChanged(editable: Editable) {
        val name:String = editable.toString()
        txtName.value = name
    }
    fun afterTextChangedL(editable: Editable) {
        val last_name:String = editable.toString()
        txtLast.value = last_name
    }

    fun afterTextChangedFilter(editable: Editable) {
        val filter: String = editable.toString()
        launchAPIRequest {
            if (filter.isEmpty()) {
                Load()
            } else {
                case.getSearch(filter).collect { search ->
                    userData.postValue(search)
                }
            }
        }
    }

    fun onClickBack(){
        sheetUser expandedDialog false
    }

    fun onClickCreate() {
        if (sheetUser?.state == BottomSheetBehavior.STATE_EXPANDED){
            sheetUser expandedDialog false
        }else{
            txtTitle.value = "Crear Usuario"
            showCreate.postValue(true)
            showDelete.postValue(false)
            showUpdate.postValue(false)
            txtName.value = ""
            txtLast.value = ""
            sheetUser expandedDialog true
        }
    }

    fun CreateUser(){
        //validacion
        val values = arrayListOf(txtName.value, txtLast.value)
        if (!values.contains("")) {
            sheetUser expandedDialog false
            launchAPIRequest {
               val person = com.prueba.hugo.data.db.room.Person(0,txtName.value!!, txtLast.value!!)
                try {
                    case.insertDataPerson(person)
                } catch (e: Exception) {
                }
            }
            //master?.hideKeyboard()
            Toast.makeText(context, "Usuario Creado", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Completa los campos", Toast.LENGTH_SHORT).show()
        }
    }

    fun UpdateDelete(data: com.prueba.hugo.data.db.room.Person){
        if (sheetUser?.state == BottomSheetBehavior.STATE_EXPANDED){
            sheetUser expandedDialog false
        }else{
            txtTitle.value = "Datos"
            showCreate.postValue(false)
            showDelete.postValue(true)
            showUpdate.postValue(true)
            txtName.value = data.name
            txtLast.value = data.last_name
            IdSelected = data.id
            sheetUser expandedDialog true
        }
    }

    fun onClickDelete(){
        launchAPIRequest {
            try {
                case.deleteById(IdSelected)
            } catch (e: Exception) { }
        }
        sheetUser expandedDialog false
        Toast.makeText(context, "Usuario Eliminado", Toast.LENGTH_SHORT).show()
    }

    fun onClickUpdate(){
        //validacion
        val values = arrayListOf(txtName.value, txtLast.value)
        if (!values.contains("")) {
            sheetUser expandedDialog false
            launchAPIRequest {
                val person = com.prueba.hugo.data.db.room.Person(
                    IdSelected,txtName.value!!, txtLast.value!!
                )
                try {
                    case.update(person)
                } catch (e: Exception) {
                }
            }
            Toast.makeText(context, "Usuario Actualizado", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Completa los campos", Toast.LENGTH_SHORT).show()
        }
    }
}