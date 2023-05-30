package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding
import com.example.a7minutesworkout.databinding.ActivityMainBinding

class ExerciseActivity : AppCompatActivity() {
    private var restTimer: CountDownTimer?=null
    private var restprogress=0
    private lateinit var binding: ActivityExerciseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        setActionBar(binding.toolbarAct)


        binding.toolbarAct.setNavigationOnClickListener {

        }
        setupRestView()

    }

    private fun setupRestView(){

        if(restTimer!=null)
        {
            restTimer?.cancel()
            restprogress=0
        }

        setRestProgressBar()
    }

    private fun setRestProgressBar(){
        binding.progbar.progress=restprogress
        restTimer=object: CountDownTimer(10000,1000)
        {
            override fun onTick(millisUntilFinished: Long) {
                restprogress++
                binding.progbar.progress=10-restprogress
                binding.timer.text=(10-restprogress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,"Here we will start the exercise.",Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTimer!=null)
        {
            restTimer?.cancel()
            restprogress=0
        }
    }
}