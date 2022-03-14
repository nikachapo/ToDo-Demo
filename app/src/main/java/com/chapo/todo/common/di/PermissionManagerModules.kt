package com.chapo.todo.common.di

import android.content.Context
import androidx.fragment.app.Fragment
import com.chapo.todo.common.utils.permissions.ActivityPermissionManager
import com.chapo.todo.common.utils.permissions.FragmentPermissionManager
import com.chapo.todo.common.utils.permissions.PermissionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Qualifier

@Module
@InstallIn(ActivityComponent::class)
object ActivityPermissionManagerModule {

    @Provides
    @ActivityPermission
    fun bindActivityPermissionManager(@ActivityContext context: Context): PermissionManager {
        return ActivityPermissionManager(context)
    }
}

@Module
@InstallIn(FragmentComponent::class)
object FragmentPermissionManagerModule {

    @Provides
    @FragmentPermission
    fun bindFragmentPermissionManager(
        fragment: Fragment
    ): PermissionManager = FragmentPermissionManager(fragment)

}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ActivityPermission

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class FragmentPermission
