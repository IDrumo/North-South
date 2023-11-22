package com.project.north_south.activity

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.north_south.databinding.ActivityLoadPageBinding

class LoadPage : AppCompatActivity() {
    private lateinit var binding : ActivityLoadPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.icon.alpha = 0f
        binding.icon.animate().setDuration(1500).alpha(1f).withEndAction{
            val i = Intent(this, LoginPage::class.java)
            val options = ActivityOptions.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out)
            startActivity(i, options.toBundle())
            finish()
        }
    }
}