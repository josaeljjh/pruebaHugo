@file:Suppress("DEPRECATION")

package com.prueba.hugo.tools.extensions

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.View.*
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.prueba.hugo.R
import com.prueba.hugo.databinding.AlertDialogBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * Created by Josaél Hernández on 12/8/20.
 * Contact : josaeljjh@gmail.com
 */

fun FragmentActivity.launch(action: suspend () -> Unit) {
    lifecycleScope.launch {
        withContext(Dispatchers.Main) {
            action.invoke()
        }
    }
}

fun FragmentActivity.setTransparentStatusBar() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = Color.TRANSPARENT
    }
    darkIcons()
}



fun FragmentActivity.darkIcons() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var flags = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        //val currentNightMode = resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)

      /* if(currentNightMode == Configuration.UI_MODE_NIGHT_NO){
            flags = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
               flags = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
           }
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            flags = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR and SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }

        window.decorView.systemUiVisibility = flags
    }
}

//Inline function for starting an Activity
inline fun <reified T : FragmentActivity> FragmentActivity.launchActivity(
    closeCurrent: Boolean = false,
    noinline init: Intent.() -> Unit = {}) {
    val i = Intent(this, T::class.java)
    i.init()
    startActivity(i)
    overridePendingTransition(R.anim.slide_in_right, android.R.anim.fade_out)
    if (closeCurrent) {
        finish()
    }
}

fun FragmentActivity.showOptionDialog(context: Context, items: List<String>, action: (value: String) -> Unit) {
    AlertDialog.Builder(context)
            .setItems(items.toTypedArray()) { dialogInterface, i ->
                action.invoke(items[i])
                dialogInterface.dismiss()
            }
            .setCancelable(true)
            .create()
            .show()
}

fun FragmentActivity.getCalendarFromDateTravel(listener: DatePickerDialog.OnDateSetListener): DatePickerDialog {

    val c = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
            this,
            listener,
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH))
    datePickerDialog.datePicker.minDate = System.currentTimeMillis()
    return datePickerDialog
}

fun FragmentActivity.showAlert(
        message: String,
        title: String,
        delete: Boolean = false,
        onSafeClick: (View) -> Unit = {},
        onCancelClick: (View) -> Unit = {}
) {


    val binding = AlertDialogBinding.inflate(layoutInflater)
    val dialog = Dialog(this, R.style.hidetitle)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(binding.root)
    dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
    dialog.setCancelable(false)
    dialog.setCanceledOnTouchOutside(false)
    dialog.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
    )
    dialog.show()
    binding.txtDescripcion.gravity = Gravity.CENTER
    binding.txtTitulo.text = title
    binding.txtDescripcion.text = message
    binding.btnAcept.setOnClickListener{
        dialog.dismiss()
        onSafeClick.invoke(it)
    }
    binding.btnCancel.setOnClickListener{
        dialog.dismiss()
        onCancelClick.invoke(it)
    }
    if(delete){
        binding.btnCancel.makeVisibility(true)
        binding.btnAcept.text = getString(R.string.cancel)
    }
}
