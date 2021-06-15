package deu.cse.tos.activity

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import deu.cse.tos.R
import kotlinx.android.synthetic.main.activity_brush.*
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.*


class BrushActivity : AppCompatActivity() {

    private lateinit var mBluetoothAdapter: BluetoothAdapter
    private lateinit var mPairedDevices: Set<BluetoothDevice>
    private lateinit var mListPairedDevices: ArrayList<String>
    private lateinit var mBluetoothHandler: Handler
    private lateinit var mThreadConnectedBluetooth: ConnectedBluetoothThread
    private lateinit var mBluetoothDevice: BluetoothDevice
    private lateinit var mBluetoothSocket: BluetoothSocket

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brush)

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        btnBluetoothOn.setOnClickListener{
            bluetoothOn()
        }
        btnBluetoothOff.setOnClickListener{
            bluetoothOff()
        }
        btnConnect.setOnClickListener{
            listPairedDevice()
        }
        btnSendData.setOnClickListener{
            mThreadConnectedBluetooth.write(tvSendData.text.toString())
            tvSendData.text.clear()
        }

        mBluetoothHandler = @SuppressLint("HandlerLeak")
        object : Handler() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun handleMessage(msg: Message) {
                if (msg.what == BT_MESSAGE_READ) {
                    var readMessage: String? = null
                    var hexString = ""
                    try {
                        val charset: Charset = Charset.forName("UTF-8")
                        readMessage = String((msg.obj as ByteArray), charset)

                        for ( i in readMessage.indices){
                            hexString += String.format("%02X", (readMessage[i].toInt() and 0xFF))
                        }
//
//                        val encoder= Base64.getEncoder()
//                        val str = encoder.encode(msg.obj as ByteArray)
//                        val encodeString = encoder.encodeToString(msg.obj as ByteArray)

                        Log.e("DEU_MSG", hexString + "\n" + readMessage )
                    } catch (e: UnsupportedEncodingException) {
                        e.printStackTrace()
                    }
                    tvReceiveData.text = readMessage?.replace("�", "")
                }
            }
        }
    }

    private fun bluetoothOn() {
        if (mBluetoothAdapter.isEnabled) {
            Toast.makeText(applicationContext, "블루투스가 이미 활성화 되어 있습니다.", Toast.LENGTH_LONG)
                .show()
            tvBluetoothStatus.setText("활성화")
        } else {
            Toast.makeText(applicationContext, "블루투스가 활성화 되어 있지 않습니다.", Toast.LENGTH_LONG)
                .show()
            val intentBluetoothEnable = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intentBluetoothEnable, BT_REQUEST_ENABLE)
        }
    }

    private fun bluetoothOff() {
        if (mBluetoothAdapter.isEnabled) {
            mBluetoothAdapter.disable()
            Toast.makeText(applicationContext, "블루투스가 비활성화 되었습니다.", Toast.LENGTH_SHORT).show()
            tvBluetoothStatus.setText("비활성화")
        } else {
            Toast.makeText(applicationContext, "블루투스가 이미 비활성화 되어 있습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            BT_REQUEST_ENABLE -> if (resultCode == RESULT_OK) { // 블루투스 활성화를 확인을 클릭하였다면
                Toast.makeText(applicationContext, "블루투스 활성화", Toast.LENGTH_LONG).show()
                tvBluetoothStatus.setText("활성화")
            } else if (resultCode == RESULT_CANCELED) { // 블루투스 활성화를 취소를 클릭하였다면
                Toast.makeText(applicationContext, "취소", Toast.LENGTH_LONG).show()
                tvBluetoothStatus.setText("비활성화")
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun listPairedDevice(){
        if (mBluetoothAdapter.isEnabled){
            mPairedDevices = mBluetoothAdapter.bondedDevices

            if(mPairedDevices.isNotEmpty()){
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle("장치 선택")

                mListPairedDevices = ArrayList()
                for (device in mPairedDevices) {
                    mListPairedDevices.add(device.name + "\n" + device.address)
                    //mListPairedDevices.add(device.getName() + "\n" + device.getAddress());
                }
                val items = mListPairedDevices.toTypedArray<CharSequence>()
                mListPairedDevices.toTypedArray<CharSequence>()

                builder.setItems(
                    items
                ) { dialog, item -> connectSelectedDevice(items[item].toString()) }

                val alert: AlertDialog = builder.create()
                alert.show()
            } else {
                Log.e("BLE", "페어링 장치 없음")
            }
        } else {
            Log.e("BLE", "블투투스 비활성화 되어있음")
        }
    }

    private fun connectSelectedDevice(selectedDeviceName: String){
        for (tempDevice in mPairedDevices) {
            if (selectedDeviceName.equals(tempDevice.name)) {
                mBluetoothDevice = tempDevice
                break
            }
        }
        try {
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(BT_UUID)
            mBluetoothSocket.connect()
            mThreadConnectedBluetooth = ConnectedBluetoothThread(mBluetoothSocket)
            mThreadConnectedBluetooth.start()
            mBluetoothHandler.obtainMessage(BT_CONNECTING_STATUS, 1, -1).sendToTarget()
        } catch (e: IOException) {
            Toast.makeText(applicationContext, "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val BT_REQUEST_ENABLE = 1
        const val BT_MESSAGE_READ = 2
        const val BT_CONNECTING_STATUS = 3
        val BT_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    }

    inner class ConnectedBluetoothThread(socket: BluetoothSocket): Thread(){
        var mmSocket: BluetoothSocket = socket
        lateinit var mmInStream: InputStream
        lateinit var mmOutStream: OutputStream

        init{
            mmSocket = socket
            var tmpIn: InputStream? = null
            var tmpOut: OutputStream? = null

            try {
                tmpIn = socket.inputStream
                tmpOut = socket.outputStream
            } catch (e: IOException) {
                Log.e("SocErr", "소켓 연결 중 에러발생")
            }
            mmInStream = tmpIn!!
            mmOutStream = tmpOut!!
        }

        override fun run() {
            val buffer = ByteArray(1024)
            var bytes: Int

            while (true) {
                try {
                    bytes = mmInStream.available()
                    if (bytes != 0) {
                        SystemClock.sleep(100)
                        bytes = mmInStream.available()
                        bytes = mmInStream.read(buffer, 0, bytes)
                        mBluetoothHandler.obtainMessage(BT_MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget()
                    }
                } catch (e: IOException) {
                    break
                }
            }
        }

        fun write(str: String) {
            val bytes = str.toByteArray()
            try {
                mmOutStream.write(bytes)
            } catch (e: IOException) {
                Toast.makeText(applicationContext, "데이터 전송 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show()
            }
        }

        fun cancel() {
            try {
                mmSocket.close()
            } catch (e: IOException) {
                Toast.makeText(applicationContext, "소켓 해제 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show()
            }
        }
    }
}