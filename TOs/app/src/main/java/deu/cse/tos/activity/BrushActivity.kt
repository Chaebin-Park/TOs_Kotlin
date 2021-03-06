package deu.cse.tos.activity

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.*
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import deu.cse.tos.R
import deu.cse.tos.data.LoginData
import kotlinx.android.synthetic.main.activity_brush.*
import kotlinx.android.synthetic.main.activity_login.*
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.*


@Suppress("DEPRECATION")
class BrushActivity : AppCompatActivity() {

    private lateinit var mBluetoothAdapter: BluetoothAdapter
    private lateinit var mPairedDevices: Set<BluetoothDevice>
    private lateinit var mListPairedDevices: ArrayList<String>
    private lateinit var mBluetoothHandler: Handler
    private lateinit var mThreadConnectedBluetooth: ConnectedBluetoothThread
    private lateinit var mBluetoothDevice: BluetoothDevice
    private lateinit var mBluetoothSocket: BluetoothSocket

    private lateinit var mIntent: Intent
    private lateinit var loginData: LoginData
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brush)

        loginData = intent.getSerializableExtra("LOGIN") as LoginData

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

        btn_end.setOnClickListener{
            mIntent = Intent(this, SelfCheckActivity::class.java)
            mIntent.putExtra("LOGIN", loginData)
            finish()
            startActivity(mIntent)
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

                        Log.e("DEU_MSG", hexString + "\n" + readMessage)
                    } catch (e: UnsupportedEncodingException) {
                        e.printStackTrace()
                    }
                    tvReceiveData.text = readMessage?.replace("???", "")
                }
            }
        }
    }

    private fun bluetoothOn() {
        if (mBluetoothAdapter.isEnabled) {
            Toast.makeText(applicationContext, "??????????????? ?????? ????????? ?????? ????????????.", Toast.LENGTH_LONG)
                .show()
            tvBluetoothStatus.setText("???????????? ?????????")
        } else {
            Toast.makeText(applicationContext, "??????????????? ????????? ?????? ?????? ????????????.", Toast.LENGTH_LONG)
                .show()
            val intentBluetoothEnable = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intentBluetoothEnable, BT_REQUEST_ENABLE)
        }
    }

    private fun bluetoothOff() {
        if (mBluetoothAdapter.isEnabled) {
            mBluetoothAdapter.disable()
            Toast.makeText(applicationContext, "??????????????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show()
            tvBluetoothStatus.setText("???????????? ????????????")
        } else {
            Toast.makeText(applicationContext, "??????????????? ?????? ???????????? ?????? ????????????.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            BT_REQUEST_ENABLE -> if (resultCode == RESULT_OK) { // ???????????? ???????????? ????????? ??????????????????
                Toast.makeText(applicationContext, "???????????? ?????????", Toast.LENGTH_LONG).show()
                tvBluetoothStatus.text = "?????????"
            } else if (resultCode == RESULT_CANCELED) { // ???????????? ???????????? ????????? ??????????????????
                Toast.makeText(applicationContext, "??????", Toast.LENGTH_LONG).show()
                tvBluetoothStatus.text = "????????????"
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun listPairedDevice(){
        if (mBluetoothAdapter.isEnabled){
            mPairedDevices = mBluetoothAdapter.bondedDevices
            log("mPairedDevices : $mPairedDevices")

            if(mPairedDevices.isNotEmpty()){
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle("?????? ??????")

                mListPairedDevices = ArrayList()
                for (device in mPairedDevices) {
                    mListPairedDevices.add(device.name)
                    //mListPairedDevices.add(device.getName() + "\n" + device.getAddress());
                }
                log("mListPairedDevices : $mListPairedDevices")
                val items = mListPairedDevices.toTypedArray<CharSequence>()
                mListPairedDevices.toTypedArray<CharSequence>()

                builder.setItems(items) { _, item ->
                    log("${items[item]}")
                    connectSelectedDevice(items[item].toString())
                }

                val alert: AlertDialog = builder.create()
                alert.show()
            } else {
                log("????????? ?????? ??????")
            }
        } else {
            log("???????????? ???????????????")
        }
    }

    private fun connectSelectedDevice(selectedDeviceName: String){
        log("mPairedDevices : $mPairedDevices")
        for (tempDevice in mPairedDevices) {
            if (selectedDeviceName.equals(tempDevice.name)) {
                mBluetoothDevice = tempDevice
                break
            }
        }
        try {
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(BT_UUID)
            mBluetoothSocket.connect()
            log("mBluetoothSocket : $mBluetoothSocket")
            log("mBluetoothDevice : $mBluetoothDevice")
            mThreadConnectedBluetooth = ConnectedBluetoothThread(mBluetoothSocket)
            mThreadConnectedBluetooth.start()
            mBluetoothHandler.obtainMessage(BT_CONNECTING_STATUS, 1, -1).sendToTarget()
        } catch (e: IOException) {
            Toast.makeText(applicationContext, "???????????? ?????? ??? ????????? ??????????????????.", Toast.LENGTH_LONG).show()
        }
    }

    private fun log(m: String){
        Log.e("BLE", m)
    }

    companion object {
        const val BT_REQUEST_ENABLE = 1
        const val BT_MESSAGE_READ = 2
        const val BT_CONNECTING_STATUS = 3
        val BT_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    }

    inner class ConnectedBluetoothThread(socket: BluetoothSocket): Thread(){
        var mmSocket: BluetoothSocket = socket
        var mmInStream: InputStream
        lateinit var mmOutStream: OutputStream

        init{
            mmSocket = socket
            var tmpIn: InputStream? = null
            var tmpOut: OutputStream? = null

            try {
                tmpIn = socket.inputStream
                tmpOut = socket.outputStream
            } catch (e: IOException) {
                Log.e("SocErr", "?????? ?????? ??? ????????????")
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
                Toast.makeText(applicationContext, "????????? ?????? ??? ????????? ??????????????????.", Toast.LENGTH_LONG).show()
            }
        }

        fun cancel() {
            try {
                mmSocket.close()
            } catch (e: IOException) {
                Toast.makeText(applicationContext, "?????? ?????? ??? ????????? ??????????????????.", Toast.LENGTH_LONG).show()
            }
        }
    }
}