import kotlinx.coroutines.delay
actual class UserLongService actual constructor() {
    actual suspend fun serve(user: User): Pair<String, User> {
        delay(3000)
        return "Native" to user
    }
}