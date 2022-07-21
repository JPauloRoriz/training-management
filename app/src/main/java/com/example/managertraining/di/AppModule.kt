package com.example.managertraining.di

import com.example.managertraining.data.repository.exercise.ExerciseRepositoryImpl
import com.example.managertraining.data.repository.exercise.contract.ExerciseRepository
import com.example.managertraining.data.repository.training.TrainingRepositoryImpl
import com.example.managertraining.data.repository.training.contract.TrainingRepository
import com.example.managertraining.data.repository.user.UserRepositoryImpl
import com.example.managertraining.data.repository.user.contract.UserRepository
import com.example.managertraining.data.service.exercise.ExerciseFirebaseService
import com.example.managertraining.data.service.exercise.contract.ExerciseService
import com.example.managertraining.data.service.training.TrainingFirebaseService
import com.example.managertraining.data.service.training.contract.TrainingService
import com.example.managertraining.data.service.user.UserFirebaseService
import com.example.managertraining.data.service.user.contract.UserService
import com.example.managertraining.domain.usecase.exercise.CreateExerciseUseCaseImpl
import com.example.managertraining.domain.usecase.exercise.DeleteExerciseUseCaseImpl
import com.example.managertraining.domain.usecase.exercise.contract.CreateExerciseUseCase
import com.example.managertraining.domain.usecase.exercise.contract.DeleteExerciseUseCase
import com.example.managertraining.domain.usecase.login.LoginUseCaseImpl
import com.example.managertraining.domain.usecase.login.contract.LoginUseCase
import com.example.managertraining.domain.usecase.register.RegisterUseCaseImpl
import com.example.managertraining.domain.usecase.register.contract.RegisterUseCase
import com.example.managertraining.domain.usecase.training.CreateTrainingUseCaseImpl
import com.example.managertraining.domain.usecase.training.DeleteTrainingUseCaseImpl
import com.example.managertraining.domain.usecase.training.GetTrainingUseCaseImpl
import com.example.managertraining.domain.usecase.training.UpdateTrainingUseCaseImpl
import com.example.managertraining.domain.usecase.training.contract.CreateTrainingUseCase
import com.example.managertraining.domain.usecase.training.contract.DeleteTrainingUseCase
import com.example.managertraining.domain.usecase.training.contract.GetTrainingsUseCase
import com.example.managertraining.domain.usecase.training.contract.UpdateTrainingUseCase
import com.example.managertraining.presentation.viewmodel.exercise.ExerciseViewModel
import com.example.managertraining.presentation.viewmodel.home.HomeViewModel
import com.example.managertraining.presentation.viewmodel.login.LoginViewModel
import com.example.managertraining.presentation.viewmodel.register.RegisterViewModel
import com.example.managertraining.presentation.viewmodel.training.EditTrainingViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    //viewModel
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegisterViewModel(get(), get()) }
    viewModel { params -> HomeViewModel(get(), params.get()) }
    viewModel { EditTrainingViewModel(get(), get(), get(), get()) }
    viewModel { params ->
        ExerciseViewModel(
            exercise = params[0],
            training = params[1],
            get(),
            get(),
            get()
        )
    }

    //useCase
    factory<RegisterUseCase> { RegisterUseCaseImpl(get(), get()) }
    factory<LoginUseCase> { LoginUseCaseImpl(get(), get()) }
    factory<DeleteTrainingUseCase> { DeleteTrainingUseCaseImpl(get()) }
    factory<GetTrainingsUseCase> { GetTrainingUseCaseImpl(get()) }
    factory<CreateExerciseUseCase> { CreateExerciseUseCaseImpl(get(), get()) }
    factory<CreateTrainingUseCase> { CreateTrainingUseCaseImpl(get(), get()) }
    factory<UpdateTrainingUseCase> { UpdateTrainingUseCaseImpl(get(), get()) }
    factory<DeleteExerciseUseCase> { DeleteExerciseUseCaseImpl(get(), get()) }

    //repository
    factory<UserRepository> { UserRepositoryImpl(get()) }
    factory<TrainingRepository> { TrainingRepositoryImpl(get()) }
    factory<ExerciseRepository> { ExerciseRepositoryImpl(get()) }

    //service
    single<UserService> { UserFirebaseService(get(), get()) }
    single<ExerciseService> { ExerciseFirebaseService(get()) }
    single<TrainingService> { TrainingFirebaseService(get()) }
    single { Firebase.firestore }
    single { Firebase.auth }
}