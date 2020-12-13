package com.prueba.hugo.data.db.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Created by Josaél Hernández on 12/12/20.
 * Contact : josaeljjh@gmail.com
 */

@Dao
interface PersonDao {

    @Query("SELECT * FROM Person")
    fun getAll(): Flow<List<Person>>

    @Query("SELECT * FROM Person WHERE id = :id")
    fun getById(id: Int): Flow<Person>

   @Query("DELETE FROM Person WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM Person WHERE name LIKE '%' || :search || '%'")
    fun getSearch(search: String?): Flow<List<Person>>

    @Update
    suspend fun update(person: Person)

    @Insert
    suspend fun insert(person: Person)

    @Delete
    suspend fun delete(person: Person)

}