package deu.cse.tos.data

import com.google.gson.annotations.SerializedName

//class QnaDTO: ArrayList<QnaDTOList>()
class QnaDTO {
    @SerializedName("result")
    var data: List<Question>? = null

    data class Question (
        @SerializedName("question_name")    val question: String,
        @SerializedName("answer")   val answer: String,
        @SerializedName("tag")  var tag: String
        )


    override fun toString(): String {
        return "QnaDTO{" +
                "data=" + data +
                '}'
    }
}