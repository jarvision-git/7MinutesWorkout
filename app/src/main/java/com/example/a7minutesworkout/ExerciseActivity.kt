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
    private var exerciseTimer: CountDownTimer?=null
    private var exerciseprogress=0
    private lateinit var binding: ActivityExerciseBinding
    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePosition=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        setActionBar(binding.toolbarAct)

        exerciseList=Constants.defaultExerciseList()


        binding.toolbarAct.setNavigationOnClickListener {

        }

        setupRestView()

    }

    private fun setupRestView(){
        binding.flprogbar.visibility=View.VISIBLE
        binding.flprogbar2.visibility=View.INVISIBLE
        binding.tvTitle.visibility=View.VISIBLE
        binding.tvExercise.visibility=View.INVISIBLE
        binding.ivimage.visibility=View.INVISIBLE
        binding.tvUpcoming.visibility=View.VISIBLE
        binding.exerciseName.visibility=View.VISIBLE


        binding.exerciseName.text= exerciseList!![currentExercisePosition+1].getName()




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
                currentExercisePosition++
                setupExerciseView()
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



    private fun setupExerciseView(){
        binding.flprogbar.visibility=View.INVISIBLE
        binding.flprogbar2.visibility=View.VISIBLE
        binding.tvTitle.visibility=View.INVISIBLE
        binding.tvExercise.visibility=View.VISIBLE
        binding.ivimage.visibility=View.VISIBLE
        binding.tvUpcoming.visibility=View.INVISIBLE
        binding.exerciseName.visibility=View.INVISIBLE

        if(exerciseTimer!=null)
        {
            exerciseTimer?.cancel()
            exerciseprogress=0
        }

        binding.ivimage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding.tvExercise.text=exerciseList!![currentExercisePosition].getName()

        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar() {
        binding.progbar2.progress=exerciseprogress
        exerciseTimer=object: CountDownTimer(30000,1000)
        {
            override fun onTick(millisUntilFinished: Long) {
                exerciseprogress++
                binding.progbar2.progress=30-exerciseprogress
                binding.timer2.text=(30-exerciseprogress).toString()

            }

            override fun onFinish() {
               if (currentExercisePosition < exerciseList?.size!! -1)
               {
                   setupRestView()
               }
                else
               {
                   Toast.makeText(this@ExerciseActivity,"Congrats, You have completed the workout",Toast.LENGTH_SHORT).show()
               }
            }

        }.start()
    }
}