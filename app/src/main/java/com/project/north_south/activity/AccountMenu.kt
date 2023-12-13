package com.project.north_south.activity

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.project.north_south.R
import com.project.north_south.viewModels.AccountFragmentViewModel
import com.project.north_south.viewModels.AccountViewModel
import com.project.north_south.databinding.ActivityAccountMenuBinding
import com.project.north_south.subAlgorithms.Storage
import com.project.north_south.viewModels.StopwatchViewModel

class AccountMenu : AppCompatActivity() {
    private lateinit var binding: ActivityAccountMenuBinding
    private lateinit var mainViewModel: AccountViewModel
    private lateinit var accountViewModel: AccountFragmentViewModel
    private lateinit var stopwatchViewModel: StopwatchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this)[AccountViewModel::class.java]
        mainViewModel.initFragmentManager(supportFragmentManager)
        accountViewModel = ViewModelProvider(this)[AccountFragmentViewModel::class.java]

        stopwatchViewModel = ViewModelProvider(this)[StopwatchViewModel::class.java]

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
                    mainViewModel.checkCameraPermission(this)
                }
            }
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val storage = Storage(this)
        stopwatchViewModel.stop()
        storage.clearCurrentData()
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