package io.lorikeet.deviceapp

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        val instant = Instant.now()

        val datetime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
        val formatted = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'").format(datetime)

        assertEquals(4, 2 + 2)
    }
}