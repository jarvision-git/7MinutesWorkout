package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import com.example.a7minutesworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FinishActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.FinisshToolbar)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        val callback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                this@FinishActivity.finish()
            }

        }
        onBackPressedDispatcher.addCallback(
            this, // LifecycleOwner
            callback
        )

        binding.btnFinish.setOnClickListener {
            this@FinishActivity.finish()
        }

    }



}