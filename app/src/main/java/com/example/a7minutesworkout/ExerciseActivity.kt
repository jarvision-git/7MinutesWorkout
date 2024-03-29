package com.example.a7minutesworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding
import com.example.a7minutesworkout.databinding.ActivityMainBinding
import com.example.a7minutesworkout.databinding.DialogBackBinding
import org.w3c.dom.Text
import java.lang.Exception
import java.util.Locale

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var finish=0
    private var restTimer: CountDownTimer?=null
    private var restprogress=0
    private var exerciseTimer: CountDownTimer?=null
    private var exerciseprogress=0
    private lateinit var binding: ActivityExerciseBinding
    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePosition=-1
    private var tts:TextToSpeech?=null
    private var player:MediaPlayer?=null

    private var exerciseAdapter:ExerciseStatusAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        tts=TextToSpeech(this,this)


        setActionBar(binding.toolbaracti)



        exerciseList=Constants.defaultExerciseList()


//        binding.toolbarAct.setNavigationOnClickListener {
//            dialogBack()
//

        val callback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                dialogBack()
            }
        }
        onBackPressedDispatcher.addCallback(
            this, // LifecycleOwner
            callback
        )


        setupRestView()
        setupExerciseStatusRecyclerView()

    }

    private fun dialogBack(){
        val customDialog= Dialog(this)
        val dialogBinding=DialogBackBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.btnYes.setOnClickListener{

            this@ExerciseActivity.finish()


            customDialog.dismiss()

        }
        dialogBinding.btnNo.setOnClickListener{
            customDialog.dismiss()

        }
        customDialog.show()
    }

    private fun setupExerciseStatusRecyclerView(){
        binding?.exerciseStatus?.layoutManager=
            LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        exerciseAdapter=ExerciseStatusAdapter(exerciseList!!)
        binding?.exerciseStatus?.adapter=exerciseAdapter
    }

    private fun setupRestView(){

        if (finish!=1) {
            try {
                val soundURI =
                    Uri.parse("android.resource://com.example.a7minutesworkout/" + R.raw.bell)
                player = MediaPlayer.create(applicationContext, soundURI)
                player?.isLooping = false
                player?.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            binding.flprogbar.visibility = View.VISIBLE
            binding.flprogbar2.visibility = View.INVISIBLE
            binding.tvTitle.visibility = View.VISIBLE
            binding.tvExercise.visibility = View.INVISIBLE
            binding.ivimage.visibility = View.INVISIBLE
            binding.tvUpcoming.visibility = View.VISIBLE
            binding.exerciseName.visibility = View.VISIBLE


            binding.exerciseName.text = exerciseList!![currentExercisePosition + 1].getName()




            if (restTimer != null) {
                restTimer?.cancel()
                restprogress = 0
            }

            setRestProgressBar()
        }
    }

    private fun setRestProgressBar(){

        if (finish!=1) {
            binding.progbar.progress = restprogress
            restTimer = object : CountDownTimer(2000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    restprogress++
                    binding.progbar.progress = 2 - restprogress
                    binding.timer.text = (2 - restprogress).toString()
                }

                override fun onFinish() {
                    currentExercisePosition++
                    exerciseList!![currentExercisePosition].setIsSelected(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupExerciseView()
                }
            }.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        finish=1

        player?.stop()
        player!!.release()
        player=null
        if (restTimer!=null)
        {
            Log.i("Status exit","rest timer Working")
            restTimer?.cancel()
            restTimer=null
            restprogress=0
        }
        if (exerciseTimer!=null)
        {
            Log.i("Status exit","exercise timer Working")
            restTimer?.cancel()
            exerciseTimer=null
            restprogress=0
        }

        if (tts!=null)
        {

            tts?.stop()
            tts?.shutdown()
        }
        Log.i("Status exit","maybe Working")
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
        speakOut(exerciseList!![currentExercisePosition].getName())
    }

    private fun setExerciseProgressBar() {
        binding.progbar2.progress=exerciseprogress
        exerciseTimer=object: CountDownTimer(5000,1000)
        {
            override fun onTick(millisUntilFinished: Long) {
                exerciseprogress++
                binding.progbar2.progress=5-exerciseprogress
                binding.timer2.text=(5-exerciseprogress).toString()

            }

            override fun onFinish() {

               if (currentExercisePosition < exerciseList?.size!! -1)
               {
                   exerciseList!![currentExercisePosition].setIsSelected(false)
                   exerciseList!![currentExercisePosition].setIsCompleted(true)
                   exerciseAdapter!!.notifyDataSetChanged()
                   setupRestView()
               }
                else
               {
                  this@ExerciseActivity.finish()

                   val intent=Intent(this@ExerciseActivity,FinishActivity::class.java)
                   startActivity(intent)
               }
            }

        }.start()
    }

    private fun speakOut(text:String)
    {
        tts?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    override fun onInit(status: Int) {
        if (status==TextToSpeech.SUCCESS)
        {
            val result=tts!!.setLanguage(Locale.US)
            if (result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("TTS","The Language specified is not supported")
            }
        }
        else
        {
            Log.e("TTS","Initialization Failed")
        }

    }
}