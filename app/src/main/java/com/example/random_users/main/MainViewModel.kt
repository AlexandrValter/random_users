package com.example.random_users.main

import androidx.lifecycle.ViewModel
import com.example.random_users.api.ErrorBody
import com.example.random_users.api.Person
import com.example.random_users.api.UserService
import com.example.random_users.api.liftEither
import com.example.random_users.common.errorParser
import com.example.random_users.database.AppDatabase
import com.example.random_users.database.UserDb
import com.example.random_users.mapper.toDomain
import com.example.random_users.mapper.toUserDb
import com.example.random_users.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject
import java.net.ConnectException

class MainViewModel(private val userService: UserService) : ViewModel() {
    private val _users = MutableStateFlow(listOf<User>())
    val users = _users.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _isError = MutableStateFlow("")
    val isError = _isError.asStateFlow()

    private val db: AppDatabase by inject(AppDatabase::class.java)
    private val dao = db.userDao()

    suspend fun loadUsers(amount: Int) {
        _isRefreshing.emit(true)
        _isError.emit("")
        withContext(Dispatchers.IO) {
            val usersFromDb = dao.getAll().map(UserDb::toDomain)
            if (usersFromDb.size >= amount) {
                _users.emit(usersFromDb.subList(0, amount))
                _isRefreshing.emit(false)
            } else {
                try {
                    userService.getUsers(amount - usersFromDb.size).liftEither()
                        .fold({
                            _isRefreshing.emit(false)
                            if (it is ErrorBody) {
                                it.raw?.let {
                                    val error = errorParser(it)
                                    if (error.isNotEmpty() && error.containsKey("error")) {
                                        _isError.emit(error["error"]!!)
                                        return@fold
                                    }
                                }
                            }
                            _isError.emit("Something went wrong, please try again later")
                        }) { userRes ->
                            userRes.results
                                .map(Person::toDomain)
                                .let {
                                    _users.emit(_users.value.plus(it))
                                    dao.insertAll(it.map(User::toUserDb))
                                }
                            _isRefreshing.emit(false)
                        }
                } catch (e: ConnectException) {
                    _isRefreshing.emit(false)
                    _isError.emit("Check your network connection settings")
                }
            }
        }
    }

    suspend fun onRefresh(amount: Int) {
        _isRefreshing.emit(true)
        withContext(Dispatchers.IO) {
            dao.clearAll()
            _users.emit(emptyList())
            loadUsers(amount)
        }
    }

    suspend fun getUserId(user: User): Int? {
        return withContext(Dispatchers.IO) {
            dao.findUserId(user.firstName, user.lastName, user.phone)
        }
    }
}
