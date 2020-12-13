package com.prueba.hugo.domain.repository

import com.prueba.hugo.data.db.realm.DataUserEntity
import com.prueba.hugo.data.db.realm.clearDataFromId
import com.prueba.hugo.data.db.realm.create
import com.prueba.hugo.data.db.realm.getData
import com.prueba.hugo.data.db.room.Person
import com.prueba.hugo.data.db.room.RoomDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

/**
 * Created by Josaél Hernández on 12/9/20.
 * Contact : josaeljjh@gmail.com
 */

class Repository(private val db: RoomDB): RepositoryCase {
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

    ///Room

    //getAllData
    override suspend fun getDataPerson(): Flow<List<Person>> = db.room.personDao().getAll()

    //insert data
    override suspend fun insertPerson(person: Person) = db.room.personDao().insert(person)

    //getById
    override suspend fun getPersonId(id: Int): Flow<Person> = db.room.personDao().getById(id)

    //delete
    override suspend fun deletePerson(person: Person) = db.room.personDao().delete(person)

    //delete by id
    override suspend fun deletePersonId(id: Int) = db.room.personDao().deleteById(id)

    //update
    override suspend fun updatePerson(person: Person) = db.room.personDao().update(person)

    //search
    override suspend fun searchPerson(search: String?): Flow<List<Person>> = db.room.personDao().getSearch(search!!)

}