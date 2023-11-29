package com.project.north_south.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AccountViewModelFactory(private val activity: AppCompatActivity, private val fragmentManager: FragmentManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            return AccountViewModel(activity, fragmentManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}