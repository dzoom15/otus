import com.otus.otuskotlin.marketplace.api.v1.models.*
import exceptions.UnknownRequestClass
import model.*
import java.time.LocalDate
import java.time.OffsetDateTime

fun WeakConContext.fromTransport(request: IRequest) = when (request) {
    is EventCreateRequest -> fromTransport(request)
    is EventReadRequest -> fromTransport(request)
    is EventUpdateRequest -> fromTransport(request)
    is EventDeleteRequest -> fromTransport(request)
    is EventsFeedRequest -> fromTransport(request)
    is EventSearchOwnRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}
fun WeakConContext.fromTransport(request: ICategoryRequest) = when (request) {
    is CategoryReadAllRequest -> fromTransport(request)
    is CategoryReadSubscribedRequest -> fromTransport(request)
    is CategorySubscribeRequest -> fromTransport(request)
    is CategoryUnsubscribeRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}
private fun String?.toEventId() = this?.let { EventId(it) } ?: EventId.NONE
private fun String?.toUserId() = this?.let { UserId(it) } ?: UserId.NONE
private fun String?.toCategoryId() = this?.let { CategoryId(it) } ?: CategoryId.NONE
private fun String?.toEventLock() = this?.let { EventLock(it) } ?: EventLock.NONE

private fun EventDebug?.transportToWorkMode(): WeakConWorkMode = when (this?.mode) {
    EventRequestDebugMode.PROD -> WeakConWorkMode.PROD
    EventRequestDebugMode.TEST -> WeakConWorkMode.TEST
    EventRequestDebugMode.STUB -> WeakConWorkMode.STUB
    null -> WeakConWorkMode.PROD
}

private fun EventDebug?.transportToStubCase(): WeakConStub = when (this?.stub) {
    EventRequestDebugStubs.SUCCESS -> WeakConStub.SUCCESS
    EventRequestDebugStubs.NOT_FOUND -> WeakConStub.NOT_FOUND
    EventRequestDebugStubs.BAD_ID -> WeakConStub.BAD_ID
    EventRequestDebugStubs.BAD_TITLE -> WeakConStub.BAD_TITLE
    EventRequestDebugStubs.BAD_DESCRIPTION -> WeakConStub.BAD_DESCRIPTION
    EventRequestDebugStubs.BAD_VISIBILITY -> WeakConStub.BAD_VISIBILITY
    EventRequestDebugStubs.CANNOT_DELETE -> WeakConStub.CANNOT_DELETE
    EventRequestDebugStubs.BAD_SEARCH_FILTER -> WeakConStub.BAD_SEARCH_FILTER
    null -> WeakConStub.NONE
}

private fun EventSearchOwnFilterObject?.toInternal(): WeakConCreatedEventFilter = WeakConCreatedEventFilter(
    finished = this?.finished ?: false,
    inProgress = this?.inProgress ?: false
)

fun WeakConContext.fromTransport(request: EventCreateRequest) {
    command = WeakConCommand.CREATE
    eventRequest = request.event?.toInternal() ?: WeakConEvent()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun WeakConContext.fromTransport(request: EventReadRequest) {
    command = WeakConCommand.READ
    eventRequest = request.event.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}


fun WeakConContext.fromTransport(request: EventUpdateRequest) {
    command = WeakConCommand.UPDATE
    eventRequest = request.event?.toInternal() ?: WeakConEvent()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun WeakConContext.fromTransport(request: EventDeleteRequest) {
    command = WeakConCommand.DELETE
    eventRequest = request.event.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun WeakConContext.fromTransport(request: EventsFeedRequest) {
    command = WeakConCommand.FEED
    eventFeedFilterRequest = request.filter?.searchFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun WeakConContext.fromTransport(request: EventSearchOwnRequest) {
    command = WeakConCommand.SEARCH_OWN
    eventCreatedEventFilterRequest = request.filter?.searchFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun WeakConContext.fromTransport(request: CategoryReadAllRequest) {
    command = WeakConCommand.READ_ALL
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun WeakConContext.fromTransport(request: CategoryReadSubscribedRequest) {
    command = WeakConCommand.READ_SUBSCRIBE
    categoryRequest = WeakConCategory(userId = request.ownerId.toUserId())
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}


fun WeakConContext.fromTransport(request: CategorySubscribeRequest) {
    command = WeakConCommand.SUBSCRIBE
    categoryRequest = WeakConCategory(id = request.categoryId.toCategoryId())
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun WeakConContext.fromTransport(request: CategoryUnsubscribeRequest) {
    command = WeakConCommand.UNSUBSCRIBE
    categoryRequest = WeakConCategory(id = request.categoryId.toCategoryId())
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun EventDeleteObject?.toInternal(): WeakConEvent = if (this != null) {
    WeakConEvent(
        id = id.toEventId(),
        lock = lock.toEventLock(),
    )
} else {
    WeakConEvent()
}

private fun EventsSearchFilterObject?.toInternal(): WeakConEventFeedFilter = WeakConEventFeedFilter(
    dateFrom = this?.dateFrom ?.let { LocalDate.parse(it) } ?: LocalDate.MIN,
    dateTo = this?.dateTo ?.let { LocalDate.parse(it) } ?: LocalDate.MIN,
    location = this?.location ?: ""
)

private fun EventCreateObject.toInternal(): WeakConEvent = WeakConEvent(
    title = this.title ?: "",
    description = this.description ?: "",
    date = OffsetDateTime.parse(this.date) ?: OffsetDateTime.MIN,
    location = this.location ?: "",
    category = this.categoriesOfEvent?.toMutableSet() ?: mutableSetOf(),
    visibility = this.visibility.fromTransport(),
)
private fun EventReadObject?.toInternal(): WeakConEvent = if (this != null) {
    WeakConEvent(
        id = this.id.toEventId()
    )
} else {
    WeakConEvent()
}
private fun EventUpdateObject.toInternal(): WeakConEvent = WeakConEvent(
    id = this.id.toEventId(),
    title = this.title ?: "",
    description = this.description ?: "",
    location = this.location ?: "",
    date = OffsetDateTime.parse(this.date) ?: OffsetDateTime.MIN,
    visibility = this.visibility.fromTransport(),
    category = this.categoriesOfEvent?.toMutableSet() ?: mutableSetOf(),
    lock = lock.toEventLock(),
)

private fun EventVisibility?.fromTransport(): WeakConVisibility = when (this) {
    EventVisibility.PUBLIC -> WeakConVisibility.VISIBLE_PUBLIC
    EventVisibility.OWNER_ONLY -> WeakConVisibility.VISIBLE_TO_OWNER
    EventVisibility.REGISTERED_ONLY -> WeakConVisibility.VISIBLE_TO_GROUP
    null -> WeakConVisibility.NONE
}