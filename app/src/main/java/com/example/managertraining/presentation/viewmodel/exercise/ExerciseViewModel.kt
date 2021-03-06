package com.example.managertraining.presentation.viewmodel.exercise

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managertraining.R
import com.example.managertraining.domain.model.ExerciseModel
import com.example.managertraining.domain.usecase.exercise.contract.CreateExerciseUseCase
import com.example.managertraining.domain.usecase.exercise.contract.DeleteExerciseUseCase
import com.example.managertraining.domain.usecase.exercise.contract.UpdateExerciseUseCase
import com.example.managertraining.presentation.viewmodel.base.SingleLiveEvent
import com.example.managertraining.presentation.viewmodel.exercise.model.ExerciseEvent
import com.example.managertraining.presentation.viewmodel.exercise.model.ExerciseState
import kotlinx.coroutines.launch

class ExerciseViewModel(
    private val idTraining: String,
    private val exercise: ExerciseModel? = null,
    private val context: Context,
    private val createExerciseUseCase: CreateExerciseUseCase,
    private val deleteExerciseUseCase: DeleteExerciseUseCase,
    private val updateExerciseUseCase: UpdateExerciseUseCase
) : ViewModel() {
    val stateLiveData = MutableLiveData(ExerciseState())
    val eventLiveData = SingleLiveEvent<ExerciseEvent>()

    private val isEdition = exercise != null

    init {
        stateLiveData.value = stateLiveData.value?.copy(
            isLoading = false,
            showTrash = isEdition,
            showIcAdd = isEdition,
            showTextAdd = isEdition,
            textButtonConfirm = if (isEdition) context.getString(R.string.edit_exercise) else context.getString(
                R.string.create_exercise
            ),
            textTitleAction = if (isEdition) context.getString(R.string.edit_exercise) else context.getString(
                R.string.tv_add_exercise
            ),
            nameExercise = if (isEdition) exercise?.name else "",
            noteExercise = if (isEdition) exercise?.note else "",
            imageExercise =  exercise?.image,
        )
    }


    fun deleteExercise() {
        viewModelScope.launch {
            stateLiveData.setLoading(true)
            deleteExerciseUseCase.invoke(exercise?.id ?: "").onSuccess {
                stateLiveData.setLoading(false)
                eventLiveData.value = ExerciseEvent.SuccessExercise(context.getString(R.string.exercise_deleted_success))
            }.onFailure {
                stateLiveData.setLoading(false)
            }
        }
    }

    fun createOrEditExercise(name: String, note: String, image: String) {
        stateLiveData.setLoading(true)
        if (exercise == null) {
            createExercise(name, note, image)
        } else {
            editExercise(exercise.id, name, note, exercise.image)
        }
    }

    private fun editExercise(id: String, name: String, note: String, image: String) {
        stateLiveData.setLoading(false)
        viewModelScope.launch {
            updateExerciseUseCase.invoke(id, name, note, image).onSuccess {
                eventLiveData.value =
                    ExerciseEvent.SuccessExercise(context.getString(R.string.exercise_edited_success))
            }.onFailure {
                stateLiveData.setLoading(false)
            }
        }
    }

    private fun createExercise(name: String, note: String, image: String) {
        viewModelScope.launch {
            idTraining.let {
                createExerciseUseCase.invoke(it, name, note, image).onSuccess {
                    stateLiveData.setLoading(false)
                    eventLiveData.value =
                        ExerciseEvent.SuccessExercise(context.getString(R.string.exercise_add_success))

                }.onFailure { error ->
                    stateLiveData.setLoading(false)
                    eventLiveData.value = ExerciseEvent.MessageError(error.message.toString())
                }
            }
        }
    }

    private fun MutableLiveData<ExerciseState>.setLoading(value: Boolean) {
        this.value = this.value?.copy(isLoading = value)
    }
}