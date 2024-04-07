import com.otus.otuskotlin.marketplace.api.v1.models.*
import java.time.OffsetDateTime
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {

    private val apiV1Mapper = WeakConApiV1Mapper

    private val eventResponse = EventCreateResponse(
        event = EventResponseObject(
            title = "walking",
            description = "slow walk",
            date = OffsetDateTime.now().toString(),
            location = "Moscow",
            categoriesOfEvent = setOf("walk"),
            visibility = EventVisibility.PUBLIC,
        )
    )

    private val categoryResponse = CategorySubscribeResponse(
        category = "run",
        result = ResponseResult.ERROR,
        errors = listOf(Error(code = "404"))
    )

    @Test
    fun serialize() {
        val eventJson = apiV1Mapper.apiV1ResponseSerialize(eventResponse)
        val categoryJson = apiV1Mapper.apiV1ResponseSerialize(categoryResponse)

        assertContains(eventJson, Regex("\"title\":\\s*\"walking\""))
        assertContains(eventJson, Regex("\"description\":\\s*\"slow walk\""))
        assertContains(eventJson, Regex("\"location\":\\s*\"Moscow\""))
        assertContains(eventJson, Regex("\"responseType\":\\s*\"create\""))
        assertContains(categoryJson, Regex("\"category\":\\s*\"run\""))
        assertContains(categoryJson, Regex("\"result\":\\s*\"error\""))
    }

    @Test
    fun eventDeserialize() {
        val json = apiV1Mapper.apiV1ResponseSerialize(eventResponse)
        val obj = apiV1Mapper.apiV1ResponseDeserialize(json) as EventCreateResponse

        assertEquals(eventResponse, obj)
    }

    @Test
    fun categoryDeserialize() {
        val json = apiV1Mapper.apiV1ResponseSerialize(categoryResponse)
        val obj = apiV1Mapper.apiV1ICategoryResponseDeserialize(json) as ICategoryResponse

        assertEquals(categoryResponse, obj)
    }
}