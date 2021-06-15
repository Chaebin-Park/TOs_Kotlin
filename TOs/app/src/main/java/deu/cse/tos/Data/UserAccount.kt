package deu.cse.tos.Data

class UserAccount {
    var hashKey: String? = null
    var nickName: String? = null

    override fun toString(): String {
        return "UserAccount{" +
                "hash_key='" + hashKey + '\'' +
                '}'
    }

    companion object {
        val instance = UserAccount()
    }
}