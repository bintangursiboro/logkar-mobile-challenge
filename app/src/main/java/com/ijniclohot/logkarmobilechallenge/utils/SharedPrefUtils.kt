package com.ijniclohot.logkarmobilechallenge.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SharedPrefUtils(private val context: Context) {

    companion object {
        const val PRIVATE_MODE = 0
        const val PREF_NAME = "appPref"
        const val REFERENCE_PREF = "referencePref"
    }

    private val sharedPreference: SharedPreferences

    init {
        sharedPreference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    @SuppressLint("CommitPrefEdits")
    fun saveReference(reference: String) {
        val editor = sharedPreference.edit()
        var mutableSetString = getReference()
        if (mutableSetString == null) {
            mutableSetString = HashSet()
            mutableSetString.add(reference)
        } else {
            mutableSetString.add(reference)
        }
        editor.putStringSet(REFERENCE_PREF, mutableSetString)
        editor.apply()
    }

    fun removeReference(reference: String) {
        val editor = sharedPreference.edit()
        var mutableSetString = getReference()
        mutableSetString?.let {
            it.remove(reference)
            editor.putStringSet(REFERENCE_PREF, it)
            editor.apply()
        }
    }

    fun getReference(): MutableSet<String>? {
        return sharedPreference.getStringSet(REFERENCE_PREF, null)
    }


}