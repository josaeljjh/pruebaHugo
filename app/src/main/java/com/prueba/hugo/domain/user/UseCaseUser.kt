package com.prueba.hugo.domain.user


import com.prueba.hugo.data.db.realm.DataUserEntity
import com.prueba.hugo.data.db.room.Person
import com.prueba.hugo.domain.repository.Repository
import io.realm.RealmResults
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

/**
 * Created by Josaél Hernández on 9/28/20.
 * Contact : josaeljjh@gmail.com
 */

@ExperimentalCoroutinesApi
class UseCaseUser(private val repo: Repository):UserCase {
    override suspend fun createData(dataUserEntity: DataUserEntity) = repo.createData(dataUserEntity)
    override suspend fun getData(): RealmResults<DataUserEntity> = repo.getDataUser()
    override suspend fun deleteData(id: String): Boolean  = repo.deleteDataId(id)

    override suspend fun getDataPerson(): Flow<List<Person>> = repo.getDataPerson()
    override suspend fun insertDataPerson(person: Person) = repo.insertPerson(person)
    override suspend fun getById(id: Int): Flow<Person> = repo.getPersonId(id)
    override suspend fun delete(person: Person) = repo.deletePerson(person)
    override suspend fun deleteById(id: Int) = repo.deletePersonId(id)
    override suspend fun update(person: Person) = repo.updatePerson(person)
    override suspend fun getSearch(search: String?): Flow<List<Person>> = repo.searchPerson(search)


}