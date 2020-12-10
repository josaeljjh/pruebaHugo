package com.prueba.hugo.domain.repository

import com.prueba.hugo.data.db.*
import io.realm.RealmResults
import kotlinx.coroutines.flow.Flow

/**
 * Created by Josaél Hernández on 12/9/20.
 * Contact : josaeljjh@gmail.com
 */
class Repository: RepositoryCase {
    //create data
    override suspend fun createData(dataUserEntity: DataUserEntity) {
        dataUserEntity.create()
    }
    //getdata()
    override suspend fun getDataUser() = DataUserEntity().getData()

    //delete
    override suspend fun deleteDataId(id:String):Boolean{
        return DataUserEntity().clearDataFromId(id)
    }

    override suspend fun getFilter(id: String): RealmResults<DataUserEntity> {
        return DataUserEntity().getDataFilter(id)
    }
}