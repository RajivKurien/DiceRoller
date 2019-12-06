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
//    fun getImageResource(diceRollerEvent: DiceRollerEvent): Int {
//
//
//        return when (val event = diceRollerEvent) {
//            is DiceRollerEvent.Reset -> {
//                resourceFetcher.fetchEmpty()
//            }
//            is DiceRollerEvent.NewDice -> {
//                getDiceImage(event.twoDice.leftDice.value)
//                getDiceImage(event.twoDice.rightDice.value)
//            }
//        }
//    }

    fun getDiceImage(dice: Dice): Int =
        when (dice) {
            Dice.ONE -> resourceFetcher.fetchOne()
            Dice.TWO -> resourceFetcher.fetchTwo()
            Dice.THREE -> resourceFetcher.fetchThree()
            Dice.FOUR -> resourceFetcher.fetchFour()
            Dice.FIVE -> resourceFetcher.fetchFive()
            Dice.SIX -> resourceFetcher.fetchSix()
        }
}