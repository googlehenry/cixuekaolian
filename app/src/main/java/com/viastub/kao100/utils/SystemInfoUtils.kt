package com.viastub.kao100.utils

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import java.net.NetworkInterface
import java.net.SocketException


object SystemInfoUtils {
    private const val TAG = "SystemInfoUtils"
    fun getInfo(context: Context): Map<String, String> {
        val infoMap: MutableMap<String, String> = LinkedHashMap()
        infoMap["设备品牌名称："] = Build.BRAND
        infoMap["设备名称："] = Build.ID
        infoMap["设备的型号："] = Build.MODEL
        infoMap["设备制造商："] = Build.MANUFACTURER
        infoMap["设备主板名称: "] = Build.BOARD
        infoMap["设备硬件名称："] = Build.HARDWARE
        infoMap["设备驱动名称："] = Build.DEVICE
        infoMap["Android ID："] =
            Settings.System.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
//        infoMap["身份识别码MEID："] = getPhoneMEID(context)
//        infoMap["设备身份码IMEI："] = getPhoneIMEI(context)
//        infoMap["序列号: "] = serial
//        infoMap["网口 MAC："] = eth0MacAddress
//        infoMap["WIFI MAC："] = getWiFiMacId(context)
        infoMap["设备的唯一标识：(由设备的多个信息拼接合成)"] = Build.FINGERPRINT
        infoMap["Android版本："] = Build.VERSION.RELEASE
        infoMap["设备版本："] = Build.VERSION.INCREMENTAL
//        infoMap["内核版本："] = kernelVersion
        return infoMap
    }

//    fun getPhoneMEID(context: Context): String {
//        val tm = context.getSystemService(Service.TELEPHONY_SERVICE) as TelephonyManager
//        var meid = ""
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            meid = tm.meid
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//            meid = tm.deviceId
//        }
//        return meid
//    }

//    fun getPhoneIMEI(context: Context): String {
//        val telephonyManager =
//            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//        val imeiList: MutableList<String> = ArrayList()
//        val imeiStr = StringBuffer()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            for (slot in 0 until telephonyManager.phoneCount) {
//                val imei = telephonyManager.getImei(slot)
//                imeiList.add(imei)
//            }
//        } else {
//            imeiList.add(telephonyManager.deviceId)
//        }
//        if (imeiList != null) {
//            var count = 0
//            for (imei in imeiList) {
//                if (count > 0) {
//                    imeiStr.append("|")
//                }
//                imeiStr.append(imei)
//                count++
//            }
//        }
//        return imeiStr.toString()
//    }

    fun getWiFiMacId(context: Context): String {
        var macAddress = "02:00:00:00:00:02"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val buf = StringBuffer()
            var networkInterface: NetworkInterface? = null
            try {
                networkInterface = NetworkInterface.getByName("wlan0")
                if (networkInterface == null) {
                    return macAddress
                }
                val addr: ByteArray = networkInterface.getHardwareAddress()
                for (b in addr) {
                    buf.append(String.format("%02X:", b))
                }
                if (buf.isNotEmpty()) {
                    buf.deleteCharAt(buf.length - 1)
                }
                macAddress = buf.toString()
            } catch (e: SocketException) {
                e.printStackTrace()
                return macAddress
            }
        } else {
            val mWifi = context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE) as WifiManager
            if (mWifi.isWifiEnabled) {
                val wifiInfo = mWifi.connectionInfo
                macAddress = if (wifiInfo != null) wifiInfo.macAddress else ""
            }
        }
        return macAddress
    }

//    val eth0MacAddress: String
//        get() {
//            var macSerial = ""
//            var str = ""
//            try {
//                val pp = Runtime.getRuntime().exec(
//                    "cat /sys/class/net/eth0/address "
//                )
//                val ir = InputStreamReader(pp.inputStream)
//                val input = LineNumberReader(ir)
//                while (null != str) {
//                    str = input.readLine()
//                    if (str != null) {
//                        macSerial = str.trim { it <= ' ' }
//                        break
//                    }
//                }
//            } catch (ex: Exception) {
//                ex.printStackTrace()
//            }
//            return macSerial
//        }
//    val kernelVersion: String
//        get() {
//            var kernelVersion = ""
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                kernelVersion = Build.VERSION.BASE_OS
//            }
//            if (TextUtils.isEmpty(kernelVersion)) {
//                kernelVersion = System.getProperty("os.version")
//            }
//            return kernelVersion
//        }
//    val serial: String
//        get() {
//            val serial: String
//            serial = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                Build.getSerial()
//            } else {
//                Build.SERIAL
//            }
//            return serial
//        }
}