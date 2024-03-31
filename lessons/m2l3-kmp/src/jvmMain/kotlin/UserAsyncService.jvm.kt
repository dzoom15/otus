actual class UserAsyncService actual constructor() {
    actual suspend fun serve(user: User): Pair<String, User> = "JVM" to user
}