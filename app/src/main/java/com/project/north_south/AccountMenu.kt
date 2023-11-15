package com.project.north_south

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.north_south.databinding.ActivityAccountMenuBinding
import com.project.north_south.databinding.ActivityLoginPageBinding
import models.FullUserInfo

class AccountMenu : AppCompatActivity() {
    lateinit var binding: ActivityAccountMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = FullUserInfo(intent)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_place, AccountFragment.newInstance(intent))
             .commit()

        binding.navigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.account -> {}
                R.id.roadmap -> {}
                R.id.qr_scanner -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_place, ScannerFragment.newInstance())
                        .commit()
                }
            }
            true
        }

//        binding.textView2.text = "login = ${user.login} \n" +
//                                 "password = ${user.password}\n" +
//                                 "role = ${user.role} \n" +
//                                 "token = ${user.token} \n"
    }
}