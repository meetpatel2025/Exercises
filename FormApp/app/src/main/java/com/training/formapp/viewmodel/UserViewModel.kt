package com.training.formapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.training.formapp.db.UserRepository
import com.training.formapp.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    var selectedUser by mutableStateOf<User?>(null)
        private set

    val users: Flow<PagingData<User>> =
        repository.getUsers()
            .cachedIn(viewModelScope)


    fun addUser(user: User) {
        viewModelScope.launch {
            repository.insert(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            repository.update(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            repository.delete(user)
        }
    }

    fun loadUser(id: Int) {
        viewModelScope.launch {
            selectedUser = repository.getUserById(id)
        }
    }
}
