package com.prueba.hugo.data.db.room

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Josaél Hernández on 12/12/20.
 * Contact : josaeljjh@gmail.com
 */

@Entity
data class Person(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var last_name: String
    )