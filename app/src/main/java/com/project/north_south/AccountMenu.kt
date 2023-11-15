package com.project.north_south

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.project.north_south.databinding.ActivityAccountMenuBinding
import com.project.north_south.databinding.ActivityLoginPageBinding
import models.FullUserInfo

class AccountMenu : AppCompatActivity() {
    private lateinit var binding: ActivityAccountMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = FullUserInfo(intent)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_place, AccountFragment.newInstance())
            .commit()

        binding.navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.account -> {
                    launchAccountFrame()
                }
                R.id.roadmap -> {
                    launchRoadmapFrame()
                }
                R.id.qr_scanner -> {
                    checkCameraPermission()
                }
            }
            true
        }
    }

    private fun launchAccountFrame(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_place, AccountFragment.newInstance())
            .commit()
    }
    private fun launchRoadmapFrame(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_place, RoadmapFragment.newInstance())
            .commit()
    }
    private fun checkCameraPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED){

            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_place, ScannerFragment.newInstance())
                .commit()

        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_place, ScannerFragment.newInstance())
                    .commit()
            }
        }
    }
}