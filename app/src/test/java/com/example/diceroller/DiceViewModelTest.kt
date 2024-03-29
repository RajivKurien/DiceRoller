package com.example.diceroller

import androidx.lifecycle.MutableLiveData
import com.example.diceroller.DiceRollEvent.NewDice
import com.example.diceroller.DiceRollEvent.Reset
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DiceViewModelTest {

    private lateinit var diceViewModel: DiceViewModel
    private val liveData = relaxedMock<MutableLiveData<DiceRollEvent>>()
    private val event = slot<DiceRollEvent>()

    @BeforeEach
    internal fun setUp() {
        every { liveData.postValue(capture(event)) } answers { Unit }
        diceViewModel = DiceViewModel(relaxedMock(), liveData)
    }

    @Test
    fun `roll posts a NewDice event`() {
        diceViewModel.roll()

        assert(event.captured is NewDice)
    }

    @Test
    fun `reset posts a Reset event`() {
        diceViewModel.reset()

        assert(event.captured is Reset)
    }

    @Test
    fun `countUp posts a NewDice event when given a NewDice event`() {
        every { liveData.value } answers { NewDice(TwoDice(Dice.ONE, Dice.ONE), 0, 0) }

        diceViewModel.countUp()

        assert(event.captured == NewDice(TwoDice(Dice.TWO, Dice.TWO), 0, 0))
    }

    @Test
    fun `countUp does not post when given a Reset event`() {
        every { liveData.value } answers { Reset }

        diceViewModel.countUp()

        verify(exactly = 0) { liveData.postValue(any()) }
    }

    @Test
    fun `rollLeft posts a NewDice event only for left dice when given a NewDice event`() {
        every { liveData.value } answers { NewDice(TwoDice(Dice.ONE, Dice.ONE), 0, 0) }

        diceViewModel.rollLeft()

        assert((event.captured as NewDice).twoDice.rightDice == Dice.ONE)
    }

    private inline fun <reified T : Any> relaxedMock() = mockk<T>(relaxed = true)
}
