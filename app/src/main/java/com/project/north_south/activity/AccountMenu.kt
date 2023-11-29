package com.project.north_south.activity

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.project.north_south.R
import com.project.north_south.viewModels.AccountFragmentViewModel
import com.project.north_south.viewModels.AccountViewModel
import com.project.north_south.viewModels.AccountViewModelFactory
import com.project.north_south.databinding.ActivityAccountMenuBinding

class AccountMenu : AppCompatActivity() {
    private lateinit var binding: ActivityAccountMenuBinding
    private lateinit var mainViewModel: AccountViewModel
    private lateinit var accountViewModel: AccountFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = AccountViewModelFactory(this, supportFragmentManager)
        mainViewModel = ViewModelProvider(this, factory)[AccountViewModel::class.java]
        accountViewModel = ViewModelProvider(this)[AccountFragmentViewModel::class.java]

        mainViewModel.launchAccountFrame()

        binding.navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.account -> {
                    mainViewModel.launchAccountFrame()
                }

                R.id.roadmap -> {
                    mainViewModel.launchRoadmapFrame()
                }

                R.id.qr_scanner -> {
                    mainViewModel.checkCameraPermission()
                }
            }
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.clearData()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mainViewModel.launchScannerFrame()
            }
        }
    }


}