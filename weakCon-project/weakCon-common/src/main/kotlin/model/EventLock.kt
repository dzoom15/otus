package model
@JvmInline
value class EventLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = EventLock("")
    }
}