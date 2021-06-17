package deu.cse.tos.activity

import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import deu.cse.tos.R
import deu.cse.tos.databinding.ActivityCameraBinding
import deu.cse.tos.helper.MyCameraPreview
import kotlinx.android.synthetic.main.activity_camera.*


@Suppress("DEPRECATION")
class CameraActivity : AppCompatActivity() {
    lateinit var binding: ActivityCameraBinding

    private val PERMISSIONS_REQUEST_CODE = 100
    private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private var CAMERA_FACING = Camera.CameraInfo.CAMERA_FACING_BACK
    private lateinit var myCameraPreview: MyCameraPreview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_camera)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // OS가 Marshmallow 이상일 경우 권한체크를 해야 합니다.
            val permissionCheckCamera
                    = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
            val permissionCheckStorage
                    = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

            if (permissionCheckCamera == PackageManager.PERMISSION_GRANTED
                    && permissionCheckStorage == PackageManager.PERMISSION_GRANTED) {
                // 권한 있음
                log("권한 이미 있음")
                startCamera()
            } else {
                // 권한 없음
                log("권한 없음")
                ActivityCompat.requestPermissions(this,
                        REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE)
            }
        } else {
            // OS가 Marshmallow 이전일 경우 권한체크를 하지 않는다.
            log("마시멜로 버전 이하로 권한 이미 있음")
            startCamera()

        }

        fb_capture.setOnClickListener{
            myCameraPreview.takePicture()

        }
    }

    private fun startCamera() {
        // Create our Preview view and set it as the content of our activity.
        myCameraPreview = MyCameraPreview(this, CAMERA_FACING)
        cameraPreview.addView(myCameraPreview)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // grantResults[0] 거부 -> -1
        // grantResults[0] 허용 -> 0 (PackageManager.PERMISSION_GRANTED)
        log("requestCode : $requestCode, grantResults size : ${grantResults.size}")

        if(requestCode == PERMISSIONS_REQUEST_CODE) {
            var checkResult = true

            for(result in grantResults) {
                if(result != PackageManager.PERMISSION_GRANTED) {
                    checkResult = false
                    break
                }
            }
            if(checkResult) {
                startCamera()
            } else {
                log("권한 거부")
            }
        }
    }

    
    private fun log(msg: String){
        Log.e("CameraActivity", msg)
    }

    companion object{
        const val REQUEST_IMAGE_CAPTURE = 672
        const val FLAG_PERM_CAMERA = 98
        const val FLAG_PERM_STORAGE = 99
        val CAMERA_PERMISSION = arrayOf(android.Manifest.permission.CAMERA)
        val STORAGE_PERMISSION = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}