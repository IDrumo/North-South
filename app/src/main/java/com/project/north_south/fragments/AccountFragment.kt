package com.project.north_south.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.project.north_south.ViewModels.AccountFragmentViewModel
import com.project.north_south.activity.LoginPage
import com.project.north_south.databinding.FragmentAccountBinding
import models.FullUserInfo


class AccountFragment : Fragment() {
    lateinit var binding: FragmentAccountBinding
    private lateinit var mainViewModel: AccountFragmentViewModel
    val userData: MutableLiveData<FullUserInfo> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater)

        mainViewModel = ViewModelProvider(this)[AccountFragmentViewModel::class.java]

        mainViewModel.updateUserInfo(binding)

        binding.reloadButton.setOnClickListener{
            mainViewModel.reloadUser(binding)
        }
        binding.exitButton.setOnClickListener{
            mainViewModel.exit()
        }
        mainViewModel.startLoginEvent.observe(requireActivity()){
            startActivity(Intent(requireContext(), LoginPage::class.java))
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AccountFragment()

    }
}