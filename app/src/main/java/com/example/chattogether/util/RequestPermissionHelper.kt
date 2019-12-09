package com.example.chattogether.util

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class RequestPermissionHelper(val activity: Activity) {

    companion object {
        const val PERMISSION_REQUEST_CODE: Int = 1
        const val PERMISSION_REQUEST_CODE_CAMERA: Int = 2
        const val PERMISSION_REQUEST_CODE_STORAGE: Int = 3
    }

    /**
     * 批量更新
     */
     fun requestPermissions(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(activity, permissions, requestCode)
        }
    }

    /**
     * 检查权限   返回true则没有
     */
     fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            permission
        ) != PackageManager.PERMISSION_GRANTED
    }

    /**
     * 申请权限
     */
    fun requestPermission(permission: String, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
    }

    /**
     *检查权限申请是否被禁止显示
     */
    fun checkIsDeny(permission:String):Boolean{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.shouldShowRequestPermissionRationale(permission)) {
                return true
            }
        }
        return false
    }
    /**
     * 检查权限后再申请
     */
    fun checkAndRequest(permission: String, requestCode: Int) {



                    requestPermission((permission), requestCode)

                log("已有{$permission}权限")


    }


    /**
     * 获取应用详情页面intent
     *
     * @return
     */
    fun getAppDetailSettingIntent(): Intent {
        val localIntent = Intent()
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
        localIntent.data = Uri.fromParts("package", activity.packageName, null)
        return localIntent
    }


}