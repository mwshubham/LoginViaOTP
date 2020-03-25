@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.loginviaotp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.loginviaotp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
        initControl()
    }

    fun init() {
        setSupportActionBar(binding.toolbar)
    }

    fun initControl() {
        binding.contentMain.btnGo.setOnClickListener {
            if (binding.contentMain.etMobileNumber.text?.length != 10) {
                Toast.makeText(this, "Please enter a valid mobile number", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            DetectingOtpDialogFragment
                .newInstance(binding.contentMain.etMobileNumber.text.toString())
                .show(supportFragmentManager, DetectingOtpDialogFragment.TAG)
        }
    }

}
