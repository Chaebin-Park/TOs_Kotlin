package deu.cse.tos.Data

import com.google.gson.annotations.SerializedName

class ToothInfoDTO {
    @SerializedName("lasttime")
    var lasttime: String? = null

    @SerializedName("difftime")
    var difftime: String? = null

    // @SerializedName으로 일치시켜 주지않을 경우엔 클래스 변수명이 일치해야함
    @SerializedName("morning_time")
    var morningTime: String? = null

    @SerializedName("afternoon_time")
    var afternoonTime: String? = null

    @SerializedName("dinner_time")
    var dinnerTime: String? = null

    @SerializedName("night_time")
    var nightTime: String? = null

    @SerializedName("morning_count")
    var morningCount = 0

    @SerializedName("afternoon_count")
    var afternoonCount = 0

    @SerializedName("dinner_count")
    var dinnerCount = 0

    @SerializedName("night_count")
    var nightCount = 0

    override fun toString(): String {
        return "ToothInfoDTO{" +
                "lasttime='" + lasttime + '\'' +
                ", difftime='" + difftime + '\'' +
                ", morning_time='" + morningTime + '\'' +
                ", afternoon_time='" + afternoonTime + '\'' +
                ", dinner_time='" + dinnerTime + '\'' +
                ", night_time='" + nightTime + '\'' +
                ", morning_count='" + morningCount + '\'' +
                ", afternoon_count='" + afternoonCount + '\'' +
                ", dinner_count='" + dinnerCount + '\'' +
                ", night_count='" + nightCount + '\'' +
                '}'
    }
}
