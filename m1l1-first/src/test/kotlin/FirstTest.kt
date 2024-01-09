import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class FirstTest {
    @Test
    fun firstTest() {
        assertEquals(3, 1 + 2)
        assertNotEquals(3, 1 + 3)
    }
}