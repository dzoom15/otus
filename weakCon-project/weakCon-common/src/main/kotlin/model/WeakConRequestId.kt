package model
@JvmInline
value class WeakConRequestId(private val rqId: String) {
    fun asString() = rqId

    companion object {
        val NONE = WeakConRequestId("")
    }
}