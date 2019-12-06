package com.example.diceroller

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ImageFinderTest {

    @Test
    fun `returns image for each die`() {
        val unit = ImageFinder(resourceFetcherStub)
        Dice.values().forEach {
            assertEquals(it.value, unit.getImageResource(SixSidedDice(it)))
        }
    }

    @Test
    fun `returns empty image for null argument`() {
        val unit = ImageFinder(resourceFetcherStub)

        assertEquals(0, unit.getImageResource(null))
    }

    private val resourceFetcherStub = object : ResourceFetcher {
        override fun fetchOne(): Int {
            return 1
        }

        override fun fetchTwo(): Int {
            return 2
        }

        override fun fetchThree(): Int {
            return 3
        }

        override fun fetchFour(): Int {
            return 4
        }

        override fun fetchFive(): Int {
            return 5
        }

        override fun fetchSix(): Int {
            return 6
        }

        override fun fetchEmpty(): Int {
            return 0
        }
    }

}