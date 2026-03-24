package com.training.formapp.db

import androidx.paging.PagingSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.training.formapp.model.User

interface UserDAO{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM User ORDER BY id DESC")
    fun getAllUser() : PagingSource<Int, User>
}