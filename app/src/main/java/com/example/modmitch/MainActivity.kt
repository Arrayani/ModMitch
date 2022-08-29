package com.example.modmitch

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.modmitch.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.nio.ByteBuffer

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        val pm: PackageManager = this.packageManager
        val hasBluetooth: Boolean = pm.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)
        //ini buat cek apakah hp ada bluetooth secara system

    }

    private fun initView() {
        isThereBT() // apakah memiliki blueetooth

    }

    private fun isThereBT() {
        val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

        if (bluetoothAdapter == null) {
            Toast.makeText(this,"Tidak mendukung bluetooth", Toast.LENGTH_SHORT).show()
            //binding.btnONOFF.isEnabled=false
            binding.connectBtn.isEnabled = false
            binding.printBtn.isEnabled = false
            return

        }else
        {
            Toast.makeText(this,"Dukungan bluetooth tersedia",Toast.LENGTH_SHORT).show()
            binding.printerStatTv.text = "Bluetooth Ready"
            cekScanBTPermission()
           // cekForBTConPermision()

        }
    }
    private fun cekScanBTPermission() {
        val mLayout = binding.rootLayout
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN)
            == PackageManager.PERMISSION_GRANTED){
           // Toast.makeText(this@MainActivity,"Sudah diberikan izin akses Scan Bluetooth",Toast.LENGTH_SHORT).show()
            //Snackbar.make(mLayout,"Sudah diberikan izin akses Scan Bluetooth", Snackbar.LENGTH_LONG).show()
        }
        else{
            Snackbar.make(mLayout,"Belum diberikan izin akses Scan Bluetooth", Snackbar.LENGTH_LONG).show()
            requestScanBTPermission()
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN)
                == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this@MainActivity,"Sudah diberikan izin akses Scan Bluetooth",Toast.LENGTH_SHORT).show()
                //Snackbar.make(mLayout,"Sudah diberikan izin akses Scan Bluetooth", Snackbar.LENGTH_LONG).show()
            }
            Snackbar.make(mLayout,"Belum diberikan izin akses Scan Bluetooth", Snackbar.LENGTH_LONG).show()
        }
    }
    private fun requestScanBTPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.BLUETOOTH_SCAN

            )){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(
                    this@MainActivity, arrayOf((Manifest.permission.BLUETOOTH_SCAN)),
                    PERMISSION_REQUEST_BLUETOOTH_SCAN
                )
            }
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(
                    this@MainActivity, arrayOf((Manifest.permission.BLUETOOTH_SCAN)),
                    PERMISSION_REQUEST_BLUETOOTH_SCAN
                )
            }

        }

    }

    companion object{
        private const val TAG = "MainActivity"
        private const val PERMISSION_REQUEST_BLUETOOTH_CONNECT = 20
        private const val PERMISSION_REQUEST_BLUETOOTH_SCAN = 30
        private const val REQUEST_CONNECT_DEVICE = 1
        private const val REQUEST_ENABLE_BT = 2

//        fun intToByteArray(value: Int): Byte {
//            val b = ByteBuffer.allocate(4).putInt(value).array()
//            for (k in b.indices) {
//                println(
//                    "Selva  [" + k + "] = " + "0x"
//                            + UnicodeFormatter.byteToHex(b[k])
//                )
//            }
//            return b[3]
//        }

    }
}