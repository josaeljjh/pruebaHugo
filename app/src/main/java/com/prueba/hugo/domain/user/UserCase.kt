package com.prueba.hugo.domain.address

import com.prueba.hugo.data.db.DataUserEntity
import io.realm.RealmResults
import kotlinx.coroutines.flow.Flow

/**
 * Created by Josaél Hernández on 12/9/20.
 * Contact : josaeljjh@gmail.com
 */
interface UserCase {
    suspend fun getData(): RealmResults<DataUserEntity>
    suspend fun createData(dataUserEntity: DataUserEntity)
    suspend fun deleteData(id:String):Boolean
    suspend fun getDataFilter(filter:String): RealmResults<DataUserEntity>
}