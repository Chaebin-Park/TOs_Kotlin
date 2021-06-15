package deu.cse.tos.Data

import com.google.gson.annotations.SerializedName

class UserDTO {
    @SerializedName("hash_key")
    private var hashKey: String? = null

    @SerializedName("created_at")
    private var createdAt: String? = null

    @SerializedName("nickname")
    private var nickname: String? = null

    @SerializedName("status")
    private var status: String? = null

    @SerializedName("age")
    private var age: String? = null

    @SerializedName("gender")
    private var gender: String? = null

    @SerializedName("level")
    private var level: String? = null

    @SerializedName("exp")
    private var exp: String? = null

    @SerializedName("tooth_score")
    private var toothScore: String? = null

    override fun toString(): String {
        return "UserDTO{" +
                "hash_key='" + hashKey + '\'' +
                ", created_at='" + createdAt + '\'' +
                ", nickname='" + nickname + '\'' +
                ", status='" + status + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", level='" + level + '\'' +
                ", exp='" + exp + '\'' +
                ", tooth_score='" + toothScore + '\'' +
                '}'
    }
}