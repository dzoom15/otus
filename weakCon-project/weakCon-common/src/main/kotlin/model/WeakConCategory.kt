package model
data class WeakConCategory(
    var id: CategoryId = CategoryId.NONE,
    var category: String = "",
    var userId: UserId = UserId.NONE,
    var visibility: WeakConVisibility = WeakConVisibility.NONE,
    var eventId: List<EventId> = listOf(EventId.NONE),
    var lock: EventLock = EventLock.NONE,
    val permissionsClient: MutableSet<WeakConPermissionsClient> = mutableSetOf()
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = WeakConCategory()
    }

}