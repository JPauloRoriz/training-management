package com.example.managertraining.di

import com.example.managertraining.data.repository.UserRepositoryImpl
import com.example.managertraining.data.repository.contract.UserRepository
import com.example.managertraining.data.service.UserFirebaseService
import com.example.managertraining.data.service.contract.UserService
import com.example.managertraining.domain.usecase.login.LoginUseCaseImpl
import com.example.managertraining.domain.usecase.login.contract.LoginUseCase
import com.example.managertraining.domain.usecase.register.RegisterUseCaseImpl
import com.example.managertraining.domain.usecase.register.contract.RegisterUseCase
import com.example.managertraining.presentation.viewmodel.login.LoginViewModel
import com.example.managertraining.presentation.viewmodel.register.RegisterViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    //viewModel
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get(), get()) }

    //useCase
    factory<RegisterUseCase> { RegisterUseCaseImpl(get(), get()) }
    factory<LoginUseCase> { LoginUseCaseImpl(get(), get()) }

    //repository
    factory<UserRepository> { UserRepositoryImpl(get()) }

    //service
    single<UserService> { UserFirebaseService(get(), get()) }
    single { Firebase.firestore }
    single { Firebase.auth }
}