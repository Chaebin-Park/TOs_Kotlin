package deu.cse.tos.Data

import com.google.gson.annotations.SerializedName

class TodayGraphDTO {
    @SerializedName("morning_count")
    private var morningCount = 0

    @SerializedName("afternoon_count")
    private var afternoonCount = 0

    // @SerializedName으로 일치시켜 주지않을 경우엔 클래스 변수명이 일치해야함

    // @SerializedName으로 일치시켜 주지않을 경우엔 클래스 변수명이 일치해야함
    @SerializedName("dinner_count")
    private var dinnerCount = 0

    @SerializedName("night_count")
    private var nightCount = 0

    override fun toString(): String {
        return "TodayGraphDTO{" +
                "morning_count=" + morningCount +
                ", afternoon_count=" + afternoonCount +
                ", dinner_count=" + dinnerCount +
                ", night_count=" + nightCount +
                '}'
    }
}