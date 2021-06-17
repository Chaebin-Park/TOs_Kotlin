package deu.cse.tos.data

import java.io.Serializable

data class QnaData (
    val question: String,
    val answer: String,
    val tag: String
) : Serializable