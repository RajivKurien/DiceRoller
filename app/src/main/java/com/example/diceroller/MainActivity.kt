package com.example.diceroller

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.diceroller.databinding.ActivityMainBinding
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var leftDice = SixSidedDieViewModel()
    private var rightDice = SixSidedDieViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            rollButton.setOnClickListener { rollDice() }
            countUpButton.setOnClickListener { countUp() }
            resetButton.setOnClickListener { reset() }
        }

        leftDice.value().observe(this, DiceObserver(binding.leftDiceImage))
        rightDice.value().observe(this, DiceObserver(binding.rightDiceImage))

        welcomeToast(this.applicationContext)
    }

    private fun reset() {
        leftDice.reset()
        rightDice.reset()
    }

    private fun welcomeToast(context: Context) =
        Toast.makeText(context, daytimeMessage(), Toast.LENGTH_SHORT).show()

    private fun rollDice() {
        leftDice.roll()
        rightDice.roll()
    }

    private fun countUp() {
        if (leftDice.value().value != 0) leftDice.next()
        if (rightDice.value().value != 0) rightDice.next()
    }

    private fun daytimeMessage(): String {
        val time = getCurrentHours()
        return "Good ${when {
            time < 12 -> getString(R.string.Morning)
            time < 18 -> getString(R.string.Afternoon)
            else -> getString(R.string.Evening)
        }}"
    }

    private fun getCurrentHours(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter
                .ofPattern("HH")
                .withZone(ZoneOffset.UTC)
                .format(Instant.now())
                .toInt()
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }
}
