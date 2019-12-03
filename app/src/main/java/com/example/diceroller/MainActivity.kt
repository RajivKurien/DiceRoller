package com.example.diceroller

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.diceroller.databinding.ActivityMainBinding
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var leftDice = SixSidedDie()
    private var rightDice = SixSidedDie()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            rollButton.setOnClickListener { rollDice() }
            countUpButton.setOnClickListener { countUp() }
            resetButton.setOnClickListener { reset() }
        }

        leftDice.value()
            .observe(this, Observer<Int> { setDiceImage(it, binding.leftDiceImage) })
        rightDice.value()
            .observe(this, Observer<Int> { setDiceImage(it, binding.rightDiceImage) })

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
        leftDice.next()
        rightDice.next()
    }

    private fun setDiceImage(i: Int, imageView: ImageView) =
        imageView.setImageResource(diceImageResource(i))

    private fun diceImageResource(i: Int): Int = when (i) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6
        else -> R.drawable.empty_dice
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
