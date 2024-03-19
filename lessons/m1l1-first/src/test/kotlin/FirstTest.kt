package com.otus.otuskotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import kotlin.test.Test

class FirstTest {
    @Test
    fun firstTest() {
        assertEquals(3, 1 + 2)
        assertNotEquals(3, 1 + 3)
    }
}