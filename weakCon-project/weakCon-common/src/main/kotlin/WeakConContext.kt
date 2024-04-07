import model.*
import java.time.Instant

data class WeakConContext(
    var command: WeakConCommand = WeakConCommand.NONE,
    var state: WeakConState = WeakConState.NONE,
    val errors: MutableList<WeakConError> = mutableListOf(),

    var workMode: WeakConWorkMode = WeakConWorkMode.PROD,
    var stubCase: WeakConStub = WeakConStub.NONE,

    var requestId: WeakConRequestId = WeakConRequestId.NONE,
    var timeStart: Instant = Instant.now(),

    var eventRequest: WeakConEvent = WeakConEvent(),
    var categoryRequest: WeakConCategory = WeakConCategory(),
    var eventFeedFilterRequest: WeakConEventFeedFilter = WeakConEventFeedFilter(),
    var eventCreatedEventFilterRequest: WeakConCreatedEventFilter = WeakConCreatedEventFilter(),

    var eventResponse: WeakConEvent = WeakConEvent(),
    var eventsResponse: MutableList<WeakConEvent> = mutableListOf(),
    var categoryResponse:  WeakConCategory = WeakConCategory(),
    var categoriesResponse: MutableList<WeakConCategory> = mutableListOf(),
)