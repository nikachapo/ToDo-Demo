package com.chapo.todo.common.data.preferences

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenPreferences @Inject constructor(
    @ApplicationContext context: Context
) : Preferences<String> {

    private val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun putValue(value: String) {
        sharedPreferences.edit { putString(KEY_TOKEN, value) }

    }

    override fun getValue(): String {
        return sharedPreferences.getString(KEY_TOKEN, "").orEmpty()
    }

    override fun clearValue() {
        sharedPreferences.edit { putString(KEY_TOKEN, null) }
    }

    companion object {
        const val PREFERENCES_NAME = "TOKEN_PREFS"
        const val KEY_TOKEN = "token"
    }
}
