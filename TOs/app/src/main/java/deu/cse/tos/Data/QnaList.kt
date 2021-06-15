package deu.cse.tos.Data

import java.io.Serializable

class QnaList(_question: String?, _answer: String?, _tag: String?) : Serializable {
    private var question: String? = null
    private var answer: String? = null
    private var tag: String? = null

    init {
        this.question = _question
        this.answer = _answer
        this.tag = _tag
    }
}