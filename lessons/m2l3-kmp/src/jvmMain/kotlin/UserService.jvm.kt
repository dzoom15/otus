actual class UserService {
    actual fun serve(user: User): String {
        val res  = "JVM Service for User $user"
        println(res)
        return res
    }
}