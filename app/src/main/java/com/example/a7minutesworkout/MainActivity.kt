package com.example.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.flStart.setOnClickListener {
            val intent= Intent(this,ExerciseActivity::class.java)
            startActivity(intent)
            
            

        }
        binding.flBMI.setOnClickListener { val intent=Intent(this,BMIActivity::class.java)
        startActivity(intent)

        }


    }


}
