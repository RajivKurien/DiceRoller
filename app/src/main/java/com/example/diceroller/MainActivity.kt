package com.example.diceroller

import android.content.Context
import android.os.Build
import android.os.Bundle
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

    private var diceViewModel = DiceViewModel()
    private val resourceFinder = ImageFinder(AndroidDiceImageResourceFetcher())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            rollButton.setOnClickListener { rollDice() }
            countUpButton.setOnClickListener { countUp() }
            resetButton.setOnClickListener { reset() }
        }

        diceViewModel.liveData.observe(this, Observer { setDiceImages(it) })
        welcomeToast(this.applicationContext)
    }

    private fun setDiceImages(map: Map<DiceKey, SixSidedDie>) {
        binding.leftDiceImage.setImageResource(resourceFinder.getImageResource(map[DiceKey.LEFT]))
        binding.rightDiceImage.setImageResource(resourceFinder.getImageResource(map[DiceKey.RIGHT]))
    }

    private fun reset() {
        diceViewModel.reset()
    }

    private fun welcomeToast(context: Context) =
        Toast.makeText(context, daytimeMessage(), Toast.LENGTH_SHORT).show()

    private fun rollDice() {
        diceViewModel.roll()
    }

    private fun countUp() {
        diceViewModel.next()
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

class AndroidDiceImageResourceFetcher : ResourceFetcher {
    override fun fetchOne() = R.drawable.dice_1
    override fun fetchTwo() = R.drawable.dice_2
    override fun fetchThree() = R.drawable.dice_3
    override fun fetchFour() = R.drawable.dice_4
    override fun fetchFive() = R.drawable.dice_5
    override fun fetchSix() = R.drawable.dice_6
    override fun fetchEmpty() = R.drawable.empty_dice
}
