package deu.cse.tos

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.auth.KakaoSDK
import deu.cse.tos.auth.KakaoSDKAdapter

class GlobalApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        instance = this
        KakaoSDK.init(KakaoSDKAdapter())
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    fun getGlobalApplicationContext(): GlobalApplication{
        checkNotNull(instance){
            "This Application does not inherit com.kakao.GlobalApplication"
        }
        return instance!!
    }


    companion object {
        var instance: GlobalApplication? = null
    }
}