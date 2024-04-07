import com.otus.otuskotlin.marketplace.api.v1.models.*
import junit.framework.TestCase.assertEquals
import model.*
import java.time.OffsetDateTime
import kotlin.test.Test

class MapperTest {
    @Test
    fun fromTransport() {
        val eventRequest = EventCreateRequest(
            debug = EventDebug(
                mode = EventRequestDebugMode.STUB,
                stub = EventRequestDebugStubs.SUCCESS
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

        val context = WeakConContext()
        context.fromTransport(eventRequest)

        assertEquals(WeakConStub.SUCCESS, context.stubCase)
        assertEquals(WeakConWorkMode.STUB, context.workMode)
        assertEquals("walking", context.eventRequest.title)
        assertEquals(WeakConVisibility.VISIBLE_PUBLIC, context.eventRequest.visibility)
    }

    @Test
    fun toTransport() {
        val context = WeakConContext(
            requestId = WeakConRequestId("1234"),
            command = WeakConCommand.CREATE,
            eventResponse = WeakConEvent(
                title = "title",
                description = "desc",
                visibility = WeakConVisibility.VISIBLE_PUBLIC,
            ),
            errors = mutableListOf(
                WeakConError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = WeakConState.RUNNING,
        )

        val req = context.toTransportEvent() as EventCreateResponse

        assertEquals("title", req.event?.title)
        assertEquals("desc", req.event?.description)
        assertEquals(EventVisibility.PUBLIC, req.event?.visibility)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}