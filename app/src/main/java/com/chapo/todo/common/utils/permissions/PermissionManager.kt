package com.chapo.todo.common.utils.permissions

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PermissionManager @Inject constructor(@ActivityContext context: Context) {

    private val activity = context as AppCompatActivity

    private var permissionListener: PermissionListener? = null

    private var permissionResultLauncher = activity
        .registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            permissionListener?.callback(granted)
        }

    private fun hasPermission(permission: String): Boolean {
        return ActivityCompat
            .checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(permission: String, permissionListener: PermissionListener) {
        this.permissionListener = permissionListener
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (hasPermission(permission)) {
                permissionListener.callback(true)
            } else {
                permissionResultLauncher.launch(permission)
            }
        } else {
            permissionListener.callback(true)
        }
    }

    fun interface PermissionListener {
        fun callback(granted: Boolean)
    }

}