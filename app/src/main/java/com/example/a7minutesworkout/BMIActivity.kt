package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.example.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    private lateinit var binding:ActivityBmiBinding
    private var unit=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.BmiToolbar)
        if (supportActionBar!=null)
        {

            supportActionBar?.title="Calculate BMI"

        }

        val callback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                this@BMIActivity.finish()
            }

        }
        onBackPressedDispatcher.addCallback(
            this, // LifecycleOwner
            callback
        )


        binding.btnCalc.setOnClickListener {
            if (validate())
            {
                val height:Float
                if(unit==true) {
                    height = binding.Height.text.toString().toFloat() / 100
                }
                else{

                    height=((binding.usHeightft.text.toString().toFloat()*30.48 + binding.usHeightin.text.toString().toFloat()*2.54).toFloat())/100

                }
                val weight:Float=binding.Weight.text.toString().toFloat()

                val bmi=weight/(height*height)

                display(bmi)
            }
            else
            {
                Toast.makeText(this@BMIActivity,"Please enter valid values.",Toast.LENGTH_SHORT).show()
            }
        }

        binding.rbUS.setOnClickListener {
            binding.tvHeight.visibility=View.INVISIBLE
            binding.tvUSHeightft.visibility=View.VISIBLE
            binding.tvUSHeightin.visibility=View.VISIBLE
            unit=false

        }
        binding.rbUnit.setOnClickListener {
            binding.tvHeight.visibility=View.VISIBLE
            binding.tvUSHeightft.visibility=View.INVISIBLE
            binding.tvUSHeightin.visibility=View.INVISIBLE
            unit=true
        }

    }

    private fun validate():Boolean{
        var isValid=true

        if( (unit==true) && binding.Height.text.toString().isEmpty())
        {
            isValid=false
        }
        else if( (unit==false) && (binding.usHeightin.text.toString().isEmpty() || binding.usHeightft.text.toString().isEmpty()) )
        {
            isValid=false
        }
        else if (binding.Weight.text.toString().isEmpty())
        {
            isValid=false
        }
        return isValid
    }

    private fun display(bmi:Float){
        binding.Verdict.visibility= View.VISIBLE
        binding.tvAns.visibility= View.VISIBLE
        binding.textView2.visibility= View.VISIBLE
        binding.result.visibility= View.VISIBLE
        val label:String
        val desc:String

        if (bmi.compareTo(15f)<=0)
        {
            label="Very Severely Underweight"
            desc="Oops! You really need to take better care of yourself! Eat more!"
        }
        else if(bmi.compareTo(15f)>0 && bmi.compareTo(16f)<=0)
        {
            label="Severely Underweight"
            desc="Oops! You really need to take better care of yourself! Eat more!"
        }
        else if (bmi.compareTo(16f)>0 && bmi.compareTo(18.5f)<=0)
        {
            label="Underweight"
            desc="Oops! You really need to take better care of yourself! Eat more!"
        }
        else if (bmi.compareTo(18.5f)>0 && bmi.compareTo(25f)<=0)
        {
            label="Normal"
            desc="Congratulations! You are in a good shape!"
        }
        else if (bmi.compareTo(25f)>0 && bmi.compareTo(30f)<=0)
        {
            label="Overweight"
            desc="Oops! You really need to take better care of yourself! Workout more!"
        }
        else if (bmi.compareTo(30f)>0 && bmi.compareTo(35f)<=0)
        {
            label="Obese Class | (moderately obese)"
            desc="Oops! You really need to take better care of yourself! Workout more!"
        }
        else if (bmi.compareTo(35f)>0 && bmi.compareTo(40f)<=0)
        {
            label="Obese Class || (severely obese)"
            desc="OMG! You are in a very dangerous condition! Act now!"
        }
        else
        {
            label="Obese Class ||| (very severely obese)"
            desc="OMG! You are in a very dangerous condition! Act now!"
        }

        val bmival=BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()
        binding.tvAns.text=bmival
        binding.result.text=label
        binding.Verdict.text=desc

    }
}