package com.example.managertraining.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.managertraining.databinding.FragmentItemTrainingBinding
import com.example.managertraining.domain.model.TrainingModel


class TrainingComponentFragment : Fragment() {
    private lateinit var binding: FragmentItemTrainingBinding
    var isEmpty: Boolean = false
    var clickTraining: ((TrainingModel) -> Unit)? = null
    val training by lazy { arguments?.getParcelable<TrainingModel>(KEY_TRAINING) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemTrainingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        binding.training.tvNameTraining.text = training?.name
        binding.training.tvDescriptionEditable.text = training?.description
        binding.training.root.setOnClickListener {
            training?.let { training -> clickTraining?.invoke(training) }
        }
        binding.trainingIsEmpty.root.setOnClickListener {
            training?.let { training -> clickTraining?.invoke(training) }
        }
        binding.training.root.isVisible = training?.isEmpty == false
        binding.trainingIsEmpty.root.isVisible = training?.isEmpty == true
    }


    companion object {
        fun newInstance(trainingModel: TrainingModel): TrainingComponentFragment {
            val args = Bundle().apply {
                putParcelable(KEY_TRAINING, trainingModel)
            }
            val fragment = TrainingComponentFragment()
            fragment.arguments = args
            return fragment
        }

        const val KEY_TRAINING = "training"
    }
}
