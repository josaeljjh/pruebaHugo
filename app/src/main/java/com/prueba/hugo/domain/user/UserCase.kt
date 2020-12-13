package com.prueba.hugo.domain.user

import androidx.room.Update
import com.prueba.hugo.data.db.realm.DataUserEntity
import com.prueba.hugo.data.db.room.Person
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

    suspend fun getDataPerson(): Flow<List<Person>>
    suspend fun insertDataPerson(person: Person)
    suspend fun getById(id: Int): Flow<Person>
    suspend fun delete(person: Person)
    suspend fun deleteById(id: Int)
    suspend fun update(person: Person)
    suspend fun getSearch(search: String?): Flow<List<Person>>
}