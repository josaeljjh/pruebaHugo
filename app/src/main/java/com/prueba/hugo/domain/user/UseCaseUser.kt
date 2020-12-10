package com.prueba.hugo.domain.address


import com.prueba.hugo.data.db.DataUserEntity
import com.prueba.hugo.domain.repository.Repository
import io.realm.RealmResults
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by Josaél Hernández on 9/28/20.
 * Contact : josaeljjh@gmail.com
 */

@ExperimentalCoroutinesApi
class UseCaseUser(private val repo: Repository):UserCase {
    override suspend fun createData(dataUserEntity: DataUserEntity) = repo.createData(dataUserEntity)
    override suspend fun getData(): RealmResults<DataUserEntity> = repo.getDataUser()
    override suspend fun deleteData(id: String): Boolean  = repo.deleteDataId(id)

}