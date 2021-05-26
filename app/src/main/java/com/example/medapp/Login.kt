package com.example.medapp

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.medapp.databinding.ActivityMainBinding

class Login : AppCompatActivity(){
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val animation : AnimationDrawable = binding.root.background as AnimationDrawable
        animation.setEnterFadeDuration(1500)
        animation.setExitFadeDuration(3000)
        animation.start()
    }

}