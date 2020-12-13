package com.prueba.hugo.data.db.realm

import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults
import io.realm.Sort

/**
 * Created by Josaél Hernández on 12/9/20.
 * Contact : josaeljjh@gmail.com
 */

 fun <T : RealmModel> T.create() {
    val realm = Realm.getDefaultInstance()
    try {
        realm.executeTransaction { it.copyToRealmOrUpdate(this) }
    } catch (e: Exception) {
        println(e.message)
        realm.cancelTransaction()
    }
}

 fun  <T : RealmModel> T.getData(): RealmResults<T> {
    val realm = Realm.getDefaultInstance()
    lateinit var data: RealmResults<T>
    try {
        realm.beginTransaction()
        data = realm.where(javaClass).findAll().sort("name", Sort.ASCENDING)
    } catch (e: Exception) {
        realm.cancelTransaction()
    } finally {
        realm.commitTransaction()
    }
    return data
}

fun <T : RealmModel> T.getDataFromId(key: String): RealmResults<T> {
    val realm = Realm.getDefaultInstance()
    lateinit var data: RealmResults<T>
    try {
        realm.beginTransaction()
        data = realm.where(javaClass)
            .equalTo("id", key)
            .findAll()
    } catch (e: Exception) {
        println(e.message)
        realm.cancelTransaction()
    } finally {
        realm.commitTransaction()
    }
    return data
}

fun <T : RealmModel> T.clearData() {
    val realm = Realm.getDefaultInstance()
    lateinit var data: RealmResults<T>
    try {
        realm.beginTransaction()
        data = realm.where(javaClass).findAll()
        data.deleteAllFromRealm()
    } catch (e: Exception) {
        println(e.message)
        realm.cancelTransaction()
    } finally {
        realm.commitTransaction()
    }
}

fun <T : RealmModel> T.clearDataFromId(key:String) :Boolean {
    val realm = Realm.getDefaultInstance()
    lateinit var data: RealmResults<T>
    try {
        realm.beginTransaction()
        data = realm.where(javaClass)
            .equalTo("id", key)
            .findAll()
        data.deleteAllFromRealm()

    } catch (e: Exception) {
        println(e.message)
        realm.cancelTransaction()
        return false
    } finally {
        realm.commitTransaction()
        return true
    }
}
