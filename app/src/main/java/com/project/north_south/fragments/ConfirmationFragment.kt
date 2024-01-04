package com.project.north_south.fragments

import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.project.north_south.R
import com.project.north_south.databinding.FragmentConfirmationBinding
import com.project.north_south.viewModels.ConfirmationFragmentViewModel

class ConfirmationFragment : Fragment() {
    private lateinit var binding: FragmentConfirmationBinding
    private lateinit var confirmationFragmentViewModel: ConfirmationFragmentViewModel
    private var callback: (() -> Unit)? = null
    private var message: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentConfirmationBinding.inflate(inflater)
        confirmationFragmentViewModel = ViewModelProvider(this)[ConfirmationFragmentViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.confirmationMessage.text = message
        binding.confirm.setOnClickListener {
            callback?.invoke()
            parentFragmentManager.beginTransaction().remove(this@ConfirmationFragment).commit()
        }
        binding.cancel.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this@ConfirmationFragment).commit()
        }
    }

    companion object {
        fun newInstance(message: String, callback: () -> Unit): ConfirmationFragment {
            val fragment = ConfirmationFragment()
            fragment.callback = callback
            fragment.message = message
            return fragment
        }
    }
}