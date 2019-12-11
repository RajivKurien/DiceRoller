package com.example.diceroller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.diceroller.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val imageFinder = ImageFinder(AndroidDiceImageResourceFetcher())
    private var viewModel = DiceViewModel(imageFinder)
    private lateinit var gridViewAdapter: GridViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainActivity = this

        gridViewAdapter = GridViewAdapter(viewModel)
        binding = DataBindingUtil.setContentView(mainActivity, R.layout.activity_main)
        binding.apply {
            rollButton.setOnClickListener { rollDice() }
            countUpButton.setOnClickListener { countUp() }
            resetButton.setOnClickListener { reset() }

            recyclerView.apply {
                layoutManager = GridLayoutManager(mainActivity, 4)
                setHasFixedSize(true)
                adapter = gridViewAdapter
            }
        }

        viewModel.liveData.observe(mainActivity, Observer { updateImages() })
    }

    private fun reset() {
        viewModel.reset()
    }

    private fun countUp() {
        viewModel.countUp()
    }


    private fun updateImages() {
        gridViewAdapter.notifyDataSetChanged()
    }

    private fun rollDice() {
        viewModel.roll()
    }
}

class AndroidDiceImageResourceFetcher : ResourceFetcher {
    override fun fetchOne() = R.drawable.dice_1
    override fun fetchTwo() = R.drawable.dice_2
    override fun fetchThree() = R.drawable.dice_3
    override fun fetchFour() = R.drawable.dice_4
    override fun fetchFive() = R.drawable.dice_5
    override fun fetchSix() = R.drawable.dice_6
}
