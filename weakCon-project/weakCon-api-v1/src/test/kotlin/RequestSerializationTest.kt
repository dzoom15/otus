import com.otus.otuskotlin.marketplace.api.v1.models.*
import java.time.OffsetDateTime
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {

    private val apiV1Mapper = WeakConApiV1Mapper

    private val eventRequest = EventCreateRequest(
        debug = EventDebug(
            mode = EventRequestDebugMode.STUB,
            stub = EventRequestDebugStubs.BAD_TITLE
        ),
        event = EventCreateObject(
            title = "walking",
            description = "slow walk",
            date = OffsetDateTime.now().toString(),
            location = "Moscow",
            categoriesOfEvent = setOf("walk"),
            visibility = EventVisibility.PUBLIC,
        )
    )
    private val categoryRequest = CategorySubscribeRequest(
        debug = EventDebug(
            mode = EventRequestDebugMode.STUB,
            stub = EventRequestDebugStubs.BAD_TITLE
        ),
        categoryId = "34"
    )

    @Test
    fun serialize() {
        val eventJson = apiV1Mapper.apiV1RequestSerialize(eventRequest)
        val categoryJson = apiV1Mapper.apiV1RequestSerialize(categoryRequest)

        assertContains(eventJson, Regex("\"title\":\\s*\"walking\""))
        assertContains(eventJson, Regex("\"description\":\\s*\"slow walk\""))
        assertContains(eventJson, Regex("\"location\":\\s*\"Moscow\""))
        assertContains(eventJson, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(eventJson, Regex("\"requestType\":\\s*\"create\""))
        assertContains(eventJson, Regex("\"mode\":\\s*\"stub\""))
        assertContains(categoryJson, Regex("\"categoryId\":\\s*\"34\""))
        assertContains(categoryJson, Regex("\"mode\":\\s*\"stub\""))
    }

    @Test
    fun deserializeEventRq() {
        val json = apiV1Mapper.apiV1RequestSerialize(eventRequest)
        val obj = apiV1Mapper.apiV1IRequestDeserialize(json) as EventCreateRequest

        assertEquals(eventRequest, obj)
    }

    @Test
    fun deserializeCategoryRq() {
        val json = apiV1Mapper.apiV1RequestSerialize(categoryRequest)
        val obj = apiV1Mapper.apiV1ICategoryRequestDeserialize(json) as CategorySubscribeRequest

        assertEquals(categoryRequest, obj)
    }
}