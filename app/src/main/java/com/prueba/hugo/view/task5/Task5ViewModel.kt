package com.prueba.hugo.view.task5

import android.text.Editable
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.prueba.hugo.App.Companion.context
import com.prueba.hugo.R
import com.prueba.hugo.data.db.DataUserEntity
import com.prueba.hugo.data.models.User
import com.prueba.hugo.data.models.UserModel
import com.prueba.hugo.databinding.ItemListBinding
import com.prueba.hugo.domain.address.UseCaseUser
import com.prueba.hugo.tools.CustomBottomSheetBehavior
import com.prueba.hugo.tools.DynamicBindingAdapter
import com.prueba.hugo.tools.extensions.expandedDialog
import com.prueba.hugo.tools.extensions.launchAPIRequest
import com.prueba.hugo.tools.extensions.setSafeOnClickListener
import io.realm.RealmResults
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by Josaél Hernández on 12/9/20.
 * Contact : josaeljjh@gmail.com
 */
@ExperimentalCoroutinesApi
class Task5ViewModel(private val case: UseCaseUser) : ViewModel() {

    var userData = MutableLiveData<List<User>>()
    var datosModel:ArrayList<User> = ArrayList()

    var txtTitle = MutableLiveData<String>()
    var txtName = MutableLiveData<String>()
    var txtLast = MutableLiveData<String>()

    var showCreate = MutableLiveData<Boolean>()
    var showDelete = MutableLiveData<Boolean>()
    var showUpdate = MutableLiveData<Boolean>()

    var IdSelected:String = ""

    //bottom sheet
    var sheetUser: CustomBottomSheetBehavior<LinearLayout>? = null

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
            val datos = case.getData()
            if(datos.isEmpty()){
                //serealizar datos
                val jsonString = context.resources.openRawResource(R.raw.users).bufferedReader().use { it.readText() }
                val data = Gson().fromJson(jsonString, UserModel::class.java)
                createBD(data.user as ArrayList<User>)
            }else{
                datosModel.clear()
                datos.forEach { realm ->
                    val user = User(id = realm.id!!, name = realm.name!!,last_name = realm.last_name!!)
                    datosModel.add(user)
                }
                userData.postValue(datosModel)
            }
        }
    }

    private fun createBD(data: ArrayList<User>) {
        launchAPIRequest {
            if (data.isNotEmpty()) {
                data.forEach { user ->
                    val User = DataUserEntity()
                    User.id = user.id
                    User.name = user.name
                    User.last_name = user.last_name
                    try {
                        case.createData(User)
                    } catch (e: Exception) {
                    }
                }
               Load()
            }
        }
    }

    fun getAdapterOptions(list:List<User>): DynamicBindingAdapter<User>? {
        var adapter: DynamicBindingAdapter<User>? = null
        try {
            adapter = DynamicBindingAdapter(
                R.layout.item_list,
                list,
                fun(vh, view, data, _) {
                    view as ItemListBinding
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
        val filter:String = editable.toString()
        if (filter.isEmpty()) {
            Load()
        }else{
            launchAPIRequest {
                val datos = case.getData()
                if (datos.isNotEmpty()) {
                    datosModel.clear()
                    datos.forEach { realm ->
                        if (realm.name!!.toLowerCase(Locale.getDefault()).startsWith(filter.toLowerCase(Locale.getDefault()))) {
                            val user = User(id = realm.id!!, name = realm.name!!, last_name = realm.last_name!!)
                            datosModel.add(user)
                        }
                    }
                    userData.postValue(datosModel)
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
                val User = DataUserEntity()
                User.id = txtName.value + txtLast.value + System.currentTimeMillis().toString()
                User.name = txtName.value
                User.last_name = txtLast.value
                try {
                    case.createData(User)
                    Load()
                } catch (e: Exception) {
                }
            }
            Toast.makeText(context, "Usuario Creado", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Completa los campos", Toast.LENGTH_SHORT).show()
        }
    }

    fun UpdateDelete(data: User){
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
            case.deleteData(IdSelected)
            Load()
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
                val User = DataUserEntity()
                User.id = IdSelected
                User.name = txtName.value
                User.last_name = txtLast.value
                try {
                    case.createData(User)
                    Load()
                } catch (e: Exception) {
                }
            }
            Toast.makeText(context, "Usuario Actualizado", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Completa los campos", Toast.LENGTH_SHORT).show()
        }
    }

    fun Load(){
        launchAPIRequest {
            val datos = case.getData()
            if (datos.isNotEmpty()) {
                datosModel.clear()
                datos.forEach { realm ->
                    val user =
                        User(id = realm.id!!, name = realm.name!!, last_name = realm.last_name!!)
                    datosModel.add(user)
                }
                userData.postValue(datosModel)
            }
        }
    }
}