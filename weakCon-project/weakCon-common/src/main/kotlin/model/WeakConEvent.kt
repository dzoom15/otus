package model

import java.time.OffsetDateTime

data class WeakConEvent(
    var id: EventId = EventId.NONE,
    var title: String = "",
    var description: String = "",
    var location: String = "",
    var date: OffsetDateTime = OffsetDateTime.now(),
    var userId: UserId = UserId.NONE,
    var visibility: WeakConVisibility = WeakConVisibility.NONE,
    var category: MutableSet<String> = mutableSetOf(),
    var lock: EventLock = EventLock.NONE,
    val permissionsClient: MutableSet<WeakConPermissionsClient> = mutableSetOf()
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = WeakConEvent()
    }

}