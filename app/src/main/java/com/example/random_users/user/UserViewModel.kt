package com.example.random_users.user

import androidx.lifecycle.ViewModel
import com.example.random_users.database.AppDatabase
import com.example.random_users.mapper.toDomain
import com.example.random_users.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent

class UserViewModel() : ViewModel() {
    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user = _user.asStateFlow()

    private val db: AppDatabase by KoinJavaComponent.inject(AppDatabase::class.java)
    private val dao = db.userDao()

    suspend fun loadUser(id: Int) {
        withContext(Dispatchers.IO) {
            val user = dao.getUser(id).toDomain()
            _user.emit(user)
        }
    }
}
