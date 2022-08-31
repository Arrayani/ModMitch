package com.example.modmitch

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

/**

 * Author: Roni Reynal Fitri  on $ {DATE} $ {HOUR}: $ {MINUTE}

 * Email: biruprinting@gmail.com

 */
class DeviceListAdapter (val onUseActivity: Context, val tvResourceId: Int, val devices: ArrayList<BluetoothDevice>)
    : ArrayAdapter<BluetoothDevice>(onUseActivity,tvResourceId,devices) {
    private val mLayoutInflater: LayoutInflater
    private val mDevices: ArrayList<BluetoothDevice>
    private val mViewResourceId: Int
    //kalo dibuat langsung private val nya pasti error, jadi harus di buat init nya
    init {
        mDevices = devices
        mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mViewResourceId = tvResourceId
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val convertView: View?
        convertView = LayoutInflater.from(parent.context).
        inflate(R.layout.device_adapter_view,parent,false)
        val device: BluetoothDevice = mDevices[position]
        if (device != null){
            val deciveName =convertView.findViewById<TextView>(R.id.tvDeviceName)
            val deviceAddress = convertView.findViewById<TextView>(R.id.tvDeviceAddress)
            deciveName.text = device.name
            deviceAddress.text = device.address
        }
        return convertView
    }


}