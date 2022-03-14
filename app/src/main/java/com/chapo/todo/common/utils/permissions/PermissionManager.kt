package com.chapo.todo.common.utils.permissions

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

abstract class PermissionManager(private val context: Context) {

    protected var permissionListener: PermissionListener? = null

    abstract val permissionResultLauncher: ActivityResultLauncher<String>

    private fun hasPermission(permission: String): Boolean {
        return ActivityCompat
            .checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
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


@ActivityScoped
class ActivityPermissionManager @Inject constructor(@ActivityContext context: Context) :
    PermissionManager(context) {

    override val permissionResultLauncher = (context as AppCompatActivity)
        .registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            permissionListener?.callback(granted)
        }
}

@FragmentScoped
class FragmentPermissionManager @Inject constructor(
    fragment: Fragment
) : PermissionManager(fragment.requireContext()) {

    override val permissionResultLauncher = fragment
        .registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            permissionListener?.callback(granted)
        }
}