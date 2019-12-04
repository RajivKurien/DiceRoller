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
    fun getImageResource(die: SixSidedDie?) = when (die) {
        SixSidedDie(1) -> resourceFetcher.fetchOne()
        SixSidedDie(2) -> resourceFetcher.fetchTwo()
        SixSidedDie(3) -> resourceFetcher.fetchThree()
        SixSidedDie(4) -> resourceFetcher.fetchFour()
        SixSidedDie(5) -> resourceFetcher.fetchFive()
        SixSidedDie(6) -> resourceFetcher.fetchSix()
        else -> resourceFetcher.fetchEmpty()
    }
}