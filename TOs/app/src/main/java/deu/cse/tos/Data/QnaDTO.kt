package deu.cse.tos.Data

import com.google.gson.annotations.SerializedName

class QnaDTO {
    @SerializedName("result")
    private var data: List<Question>? = null

    class Question {
        @SerializedName("question_name")
        var questionName: String? = null

        @SerializedName("answer")
        var answer: String? = null

        @SerializedName("tag")
        var tag: String? = null
        override fun toString(): String {
            return "Question{" +
                    "question_name='" + questionName + '\'' +
                    ", answer='" + answer + '\'' +
                    ", tag='" + tag + '\'' +
                    '}'
        }
    }

    override fun toString(): String {
        return "QnaDTO{" +
                "data=" + data +
                '}'
    }
}