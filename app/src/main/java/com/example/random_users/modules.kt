package com.example.random_users

import com.example.random_users.main.MainViewModel
import com.example.random_users.user.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel {
        MainViewModel(get())
    }
    viewModel {
        UserViewModel()
    }
}