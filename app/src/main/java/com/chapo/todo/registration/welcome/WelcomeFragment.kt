package com.chapo.todo.registration.welcome

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chapo.todo.tasks.TasksActivity
import com.chapo.todo.databinding.WelcomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    private var _binding: WelcomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WelcomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btFinish.setOnClickListener {
            requireActivity().startActivity(Intent(requireActivity(), TasksActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}