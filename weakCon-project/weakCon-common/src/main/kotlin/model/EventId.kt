package model
@JvmInline
value class EventId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = EventId("")
    }
}