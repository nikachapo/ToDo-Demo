package com.chapo.todo.common.data.preferences

import android.content.Context
import com.chapo.todo.common.domain.user.User
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataPreferences @Inject constructor(
    @ApplicationContext context: Context
) : Preferences<User> {

    private val moshi = Moshi.Builder().build()

    private val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun putValue(value: User) {
        sharedPreferences.edit { putString(KEY_USER_DATA, value.toJson()) }
    }

    private fun User.toJson(): String? {
        val adapter = moshi.adapter(User::class.java)
        return adapter.toJson(this)
    }

    private fun fromJson(jsonString: String?): User? {
        jsonString ?: return null
        val adapter = moshi.adapter(User::class.java)
        return try {
            adapter.fromJson(jsonString)
        } catch (e: IOException) {
            null
        }
    }

    override fun getValue(): User? {
        return fromJson(sharedPreferences.getString(KEY_USER_DATA, null))
    }

    override fun clearValue() {
        sharedPreferences.edit { putString(KEY_USER_DATA, null) }
    }

    companion object {
        const val PREFERENCES_NAME = "USER_DATA_PREFS"
        const val KEY_USER_DATA = "user-data"
    }

}