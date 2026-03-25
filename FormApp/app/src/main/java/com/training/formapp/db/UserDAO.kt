package com.training.formapp.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.training.formapp.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM User ORDER BY id DESC")
    fun getAllUser(): PagingSource<Int, User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM User WHERE id = :id")
    suspend fun getUserById(id: Int): User?
}