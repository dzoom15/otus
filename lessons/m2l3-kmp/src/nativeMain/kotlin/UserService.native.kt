actual class UserService {
    actual fun serve(user: User): String{
        val res = "Native Service for User $user"
        println(res)
        return res
    }
}