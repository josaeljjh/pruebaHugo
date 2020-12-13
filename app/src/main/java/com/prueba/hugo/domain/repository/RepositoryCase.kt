package com.prueba.hugo.domain.repository


import com.prueba.hugo.data.db.realm.DataUserEntity
import com.prueba.hugo.data.db.room.Person
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

    suspend fun getDataPerson(): Flow<List<Person>>
    suspend fun insertPerson(person: Person)
    suspend fun getPersonId(id: Int): Flow<Person>
    suspend fun deletePerson(person: Person)
    suspend fun deletePersonId(id: Int)
    suspend fun updatePerson(person: Person)
    suspend fun searchPerson(search: String?): Flow<List<Person>>
}