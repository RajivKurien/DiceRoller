package com.example.diceroller

interface ResourceFetcher {
    fun fetchOne(): Int
    fun fetchTwo(): Int
    fun fetchThree(): Int
    fun fetchFour(): Int
    fun fetchFive(): Int
    fun fetchSix(): Int
    fun fetchEmpty(): Int
}

class ImageFinder(private val resourceFetcher: ResourceFetcher) {
    fun getImageResource(dice: SixSidedDice?) = when (dice) {
        SixSidedDice(Dice.ONE) -> resourceFetcher.fetchOne()
        SixSidedDice(Dice.TWO) -> resourceFetcher.fetchTwo()
        SixSidedDice(Dice.THREE) -> resourceFetcher.fetchThree()
        SixSidedDice(Dice.FOUR) -> resourceFetcher.fetchFour()
        SixSidedDice(Dice.FIVE) -> resourceFetcher.fetchFive()
        SixSidedDice(Dice.SIX) -> resourceFetcher.fetchSix()
        else -> resourceFetcher.fetchEmpty()
    }
}