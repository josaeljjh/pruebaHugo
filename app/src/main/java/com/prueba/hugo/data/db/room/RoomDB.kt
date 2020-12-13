package com.prueba.hugo.data.db.room

import androidx.room.Room
import com.prueba.hugo.App

/**
 * Created by Josaél Hernández on 12/12/20.
 * Contact : josaeljjh@gmail.com
 */
class RoomDB {
    // Room Database instance
    val room = Room
        .databaseBuilder(App.context, PersonDB::class.java, "person")
        .build()
}