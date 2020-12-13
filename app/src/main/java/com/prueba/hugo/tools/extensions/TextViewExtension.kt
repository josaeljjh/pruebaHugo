package com.prueba.hugo.tools.extensions

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import com.prueba.hugo.R
import com.prueba.hugo.data.models.User

/**
 * Created by Josaél Hernández on 12/9/20.
 * Contact : josaeljjh@gmail.com
 */

@SuppressLint("SetTextI18n")
@BindingAdapter("txtName")
fun TextView.setName(user: User) {
    this.text = "${user.name} ${user.last_name}"
}

@BindingAdapter("txtName2")
fun TextView.setName2(user: com.prueba.hugo.data.db.room.Person) {
    this.text = "${user.name} ${user.last_name}"
}