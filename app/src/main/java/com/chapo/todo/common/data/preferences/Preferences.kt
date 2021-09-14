package com.chapo.todo.common.data.preferences

import android.content.SharedPreferences

interface Preferences<Value> {

    fun putValue(value: Value)

    fun getValue(): Value?

    fun clearValue()
}

inline fun SharedPreferences.edit(block: SharedPreferences.Editor.() -> Unit) {
    with(edit()) {
        block()
        commit()
    }
}