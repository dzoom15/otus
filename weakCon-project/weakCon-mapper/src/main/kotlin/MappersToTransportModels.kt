import com.otus.otuskotlin.marketplace.api.v1.models.*
import exceptions.UnknownCommandClass
import model.*
import java.time.OffsetDateTime

fun WeakConContext.toTransportEvent(): IResponse = when (val cmd = command) {
    WeakConCommand.CREATE -> toTransportCreate()
    WeakConCommand.READ -> toTransportRead()
    WeakConCommand.UPDATE -> toTransportUpdate()
    WeakConCommand.DELETE -> toTransportDelete()
    WeakConCommand.FEED -> toTransportSearch()
    WeakConCommand.SEARCH_OWN -> toTransportOffers()
    WeakConCommand.NONE -> throw UnknownCommandClass(cmd)
    else -> { throw UnknownCommandClass(cmd) }
}

fun WeakConContext.toTransportCategory(): ICategoryResponse = when (val cmd = command) {
    WeakConCommand.READ_ALL -> toTransportReadAll()
    WeakConCommand.READ_SUBSCRIBE -> toTransportReadSubscribe()
    WeakConCommand.SUBSCRIBE -> toTransportSubscribe()
    WeakConCommand.UNSUBSCRIBE -> toTransportUnsubscribe()
    WeakConCommand.NONE -> throw UnknownCommandClass(cmd)
    else -> { throw UnknownCommandClass(cmd) }
}

fun WeakConContext.toTransportCreate() = EventCreateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    event = eventResponse.toTransportEvent()
)

fun WeakConContext.toTransportRead() = EventReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    event = eventResponse.toTransportEvent()
)

fun WeakConContext.toTransportUpdate() = EventUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    event = eventResponse.toTransportEvent()
)

fun WeakConContext.toTransportDelete() = EventDeleteResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    event = eventResponse.toTransportEvent()
)

fun WeakConContext.toTransportSearch() = EventsFeedResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    events = eventsResponse.toTransportEvent()
)

fun WeakConContext.toTransportOffers() = EventSearchOwnResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    events = eventsResponse.toTransportEvent()
)

fun WeakConContext.toTransportReadAll() = CategoryReadAllResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    categories = categoriesResponse.map { it.toTransportEvent() }
)

fun WeakConContext.toTransportReadSubscribe() = CategoryReadSubscribedResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    categories = categoriesResponse.map { it.toTransportEvent() }
)

fun WeakConContext.toTransportSubscribe() = CategorySubscribeResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    category = categoryResponse.toTransportEvent().category
)

fun WeakConContext.toTransportUnsubscribe() = CategoryUnsubscribeResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    category = categoryResponse.toTransportEvent().category
)

fun List<WeakConEvent>.toTransportEvent(): List<EventResponseObject>? = this
    .map { it.toTransportEvent() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun WeakConEvent.toTransportEvent(): EventResponseObject = EventResponseObject(
    id = id.takeIf { it != EventId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    location = location.takeIf { it.isNotBlank() },
    date = date.takeIf { it != OffsetDateTime.MIN }?.toString(),
    categoriesOfEvent = category.takeIf { it.isNotEmpty() },
    ownerId = userId.takeIf { it != UserId.NONE }?.asString(),
    visibility = visibility.toTransportEvent(),
    permissions = permissionsClient.toTransportEvent(),
)

private fun WeakConCategory.toTransportEvent(): CategoryResponseSingle = CategoryResponseSingle(
    category = category
)

private fun Set<WeakConPermissionsClient>.toTransportEvent(): Set<EventPermissions>? = this
    .map { it.toTransportEvent() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun WeakConPermissionsClient.toTransportEvent() = when (this) {
    WeakConPermissionsClient.READ -> EventPermissions.READ
    WeakConPermissionsClient.UPDATE -> EventPermissions.UPDATE
    WeakConPermissionsClient.MAKE_VISIBLE_OWNER -> EventPermissions.MAKE_VISIBLE_OWN
    WeakConPermissionsClient.MAKE_VISIBLE_GROUP -> EventPermissions.MAKE_VISIBLE_GROUP
    WeakConPermissionsClient.MAKE_VISIBLE_PUBLIC -> EventPermissions.MAKE_VISIBLE_PUBLIC
    WeakConPermissionsClient.DELETE -> EventPermissions.DELETE
}

private fun WeakConVisibility.toTransportEvent(): EventVisibility? = when (this) {
    WeakConVisibility.VISIBLE_PUBLIC -> EventVisibility.PUBLIC
    WeakConVisibility.VISIBLE_TO_GROUP -> EventVisibility.REGISTERED_ONLY
    WeakConVisibility.VISIBLE_TO_OWNER -> EventVisibility.OWNER_ONLY
    WeakConVisibility.NONE -> null
}

private fun List<WeakConError>.toTransportErrors(): List<com.otus.otuskotlin.marketplace.api.v1.models.Error>? =
    this.map { it.toTransportEvent() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun WeakConError.toTransportEvent() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

private fun WeakConState.toResult(): ResponseResult? = when (this) {
    WeakConState.RUNNING -> ResponseResult.SUCCESS
    WeakConState.FAILING -> ResponseResult.ERROR
    WeakConState.FINISHING -> ResponseResult.SUCCESS
    WeakConState.NONE -> null
}