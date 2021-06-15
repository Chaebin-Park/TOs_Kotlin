package deu.cse.tos.Data

import com.google.gson.annotations.SerializedName

class CheckResultDTO {
    @SerializedName("result")
    private var result: String? = null

    fun getResult(): String? {
        return this.result
    }

    fun setResult(result: String?) {
        this.result = result
    }

    override fun toString(): String {
        return "CheckResult{" +
                "result='" + result + '\'' +
                '}'
    }
}