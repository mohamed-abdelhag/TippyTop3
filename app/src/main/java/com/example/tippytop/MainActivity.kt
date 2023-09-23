package com.example.tippytop

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tippytop.databinding.ActivityMainBinding
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {

    private lateinit var cajiga: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cajiga = ActivityMainBinding.inflate(layoutInflater)
        setContentView(cajiga.root)

        cajiga.calculateButton.setOnClickListener{ calculateTip() }

    }

    private fun calculateTip(){
        val tipInString = cajiga.costOfServiceEditText.text.toString()

        val cost = tipInString.toDoubleOrNull()
        if (cost == null || cost == 0.0 ){
            displayTip(0.0)
            return
        }

        val tipPercentage =
            when(cajiga.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
            }

        var tip = cost * tipPercentage


        if (cajiga.roundUpSwitch.isChecked){ tip = kotlin.math.ceil(tip)}

        displayTip(tip)

    }

    private fun displayTip(tip: Double) {
        val tipy = NumberFormat.getCurrencyInstance().format(tip)
        cajiga.tipResult.text = getString(R.string.tip_amount , tipy)

    }


    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

}
