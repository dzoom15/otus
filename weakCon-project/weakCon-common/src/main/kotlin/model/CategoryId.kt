package model
@JvmInline
value class CategoryId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = CategoryId("")
    }
}