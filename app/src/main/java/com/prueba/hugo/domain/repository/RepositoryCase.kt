package com.prueba.hugo.domain.repository


import com.prueba.hugo.data.db.DataUserEntity
import io.realm.RealmResults
import kotlinx.coroutines.flow.Flow

/**
 * Created by Josaél Hernández on 12/9/20.
 * Contact : josaeljjh@gmail.com
 */

interface RepositoryCase {
    suspend fun getDataUser(): RealmResults<DataUserEntity>
    suspend fun createData(dataUserEntity: DataUserEntity)
    suspend fun deleteDataId(id:String):Boolean
    suspend fun getFilter(filter:String): RealmResults<DataUserEntity>
}