package com.prueba.hugo.data.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by Josaél Hernández on 12/9/20.
 * Contact : josaeljjh@gmail.com
 */
open class DataUserEntity: RealmObject() {
    @PrimaryKey
    var id: String? = ""
    var name: String? = ""
    var last_name: String? = ""

}