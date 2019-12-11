package com.example.diceroller

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class GridViewAdapter(private val viewModel: DiceViewModel) :
    RecyclerView.Adapter<GridViewAdapter.DiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiceViewHolder {
        val imageView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.dice_layout, parent, false)

        return DiceViewHolder(imageView as ImageView)
    }

    override fun getItemCount() =
        when (val event = viewModel.liveData.value) {
            is DiceRollEvent.NewDice -> event.list.size
            else -> 0
        }


    override fun onBindViewHolder(holder: DiceViewHolder, position: Int) {
        when (val event = viewModel.liveData.value) {
            is DiceRollEvent.NewDice -> holder.imageView.setImageResource(event.list[position].image)
            else -> holder.imageView.setImageResource(R.drawable.empty_dice)
        }

        holder.imageView.setOnClickListener {
            viewModel.rollDice(position)
        }
    }

    class DiceViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView)

}
