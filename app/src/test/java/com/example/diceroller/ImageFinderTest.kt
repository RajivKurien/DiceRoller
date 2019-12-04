package com.example.diceroller

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ImageFinderTest {

    @ParameterizedTest
    @CsvSource("1", "2", "3", "4", "5", "6")
    fun `returns image for each die`(value: Int) {
        val unit = ImageFinder(ResourceFetcherStub)

        assertEquals(value, unit.getImageResource(SixSidedDie(value)))
    }

    @Test
    fun `returns empty image for null argument`() {
        val unit = ImageFinder(ResourceFetcherStub)

        assertEquals(0, unit.getImageResource(null))
    }

    val ResourceFetcherStub = object : ResourceFetcher {
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