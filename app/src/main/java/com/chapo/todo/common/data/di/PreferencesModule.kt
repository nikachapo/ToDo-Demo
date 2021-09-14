package com.chapo.todo.common.data.di

import com.chapo.todo.common.data.preferences.TokenPreferences
import com.chapo.todo.common.data.preferences.Preferences
import com.chapo.todo.common.data.preferences.UserDataPreferences
import com.chapo.todo.common.domain.user.User
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class Token

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class UserData

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

  @Binds
  @Token
  abstract fun providePreferences(preferences: TokenPreferences): Preferences<String>

  @Binds
  @UserData
  abstract fun provideUserDataPrefs(userDataPreferences: UserDataPreferences): Preferences<User>
}
