package com.prueba.hugo.data.db.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.prueba.hugo.App

/**
 * Created by Josaél Hernández on 12/12/20.
 * Contact : josaeljjh@gmail.com
 */

@Database(
    entities = [Person::class],
    version = 1,
    exportSchema = true
)
abstract  class PersonDB : RoomDatabase() {

    abstract  fun  personDao():PersonDao

}