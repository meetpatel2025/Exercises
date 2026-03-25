package com.training.formapp.db

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.training.formapp.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository
@Inject constructor(
    private val userDao: UserDao
) {
    fun getUsers(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { userDao.getAllUser() }
        ).flow
    }

    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    suspend fun update(user: User) {
        userDao.update(user)
    }

    suspend fun delete(user: User) {
        userDao.delete(user)
    }

    suspend fun getUserById(id: Int): User? {
        return userDao.getUserById(id)
    }
}
