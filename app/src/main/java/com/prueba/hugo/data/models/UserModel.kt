package com.prueba.hugo.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Josaél Hernández on 11/27/20.
 * Contact : josaeljjh@gmail.com
 */


@Parcelize
data class UserModel(
    val user: List<User>
) : Parcelable

@Parcelize
data class User(
    val id: String,
    val name: String,
    val last_name: String,
): Parcelable