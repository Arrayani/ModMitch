package com.example.modmitch

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import java.lang.Exception

class DeviceListActivity : AppCompatActivity() {
    // private lateinit var mBluetoothAdapter: BluetoothAdapter
    private var mPairedDevicesArrayAdapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS)
        setContentView(R.layout.activity_device_list)
        val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

        setResult(RESULT_CANCELED)

        // mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        mPairedDevicesArrayAdapter = ArrayAdapter(this, R.layout.device_name) //ini layout beda lagi

        val mPairedListView = findViewById<ListView>(R.id.paired_devices) //ini listviewnya
        mPairedListView.adapter = mPairedDevicesArrayAdapter
        mPairedListView.onItemClickListener = mDeviceClickListener

        val mPairedDevices = bluetoothAdapter!!.bondedDevices

        if ( mPairedDevices.size>0) {
            findViewById<View>(R.id.title_paired_devices).visibility =
                View.VISIBLE // menampilkan tulisan paired devices
            for (mDevice in mPairedDevices){
                mPairedDevicesArrayAdapter!!.add("""${mDevice.name}${mDevice.address}""".trimIndent())
            }
        }else{
            /* No paired device */
            val mNoDevices = "None Paired"
            mPairedDevicesArrayAdapter!!.add(mNoDevices)
        }
    }

    private val mDeviceClickListener=
        //AdapterView.OnItemClickListener{ AdapterView,View,int,Long->
        AdapterView.OnItemClickListener { mAdapterView, mView, mPosition, mLong ->
            val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
            val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
            try {
                /* Attempt to connect to bluetooth device */
                bluetoothAdapter!!.cancelDiscovery() //kenapa cancel discovery??
                val mDeviceInfo =(mView as TextView).text.toString()
                //val mDeviceInfo = findViewById<TextView>(R.id.)
                val mDeviceAddress = mDeviceInfo.substring(mDeviceInfo.length - 17)
                Log.v(TAG,"Device_Address $mDeviceAddress")
                //ini proses mengirim data ke activity sebelumnya,
                //data nya berupa MAC Address
                val mBundle = Bundle()
                mBundle.putString("DeviceAddress", mDeviceAddress) //datanya
                val mBackIntent = Intent()
                mBackIntent.putExtras(mBundle)
                setResult(RESULT_OK, mBackIntent)
                finish()
            }catch (ex: Exception) {
                Log.v(TAG, "Error: $ex")
            }
        }
    companion object {
        private const val TAG = "DeviceListActivity"
    }
}