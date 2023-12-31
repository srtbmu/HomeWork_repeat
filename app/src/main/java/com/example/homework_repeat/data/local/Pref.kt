package com.example.homework_repeat.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Pref(context: Context) {

    private val pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)

    fun isOnBoardingShowed(): Boolean {
        return pref.getBoolean(SHOWED_KEY, false)
    }

    fun onBoardingShowed() {
        pref.edit().putBoolean(SHOWED_KEY, true).apply()
    }

    fun saveName(name: String) {
        pref.edit().putString(NAME_KEY, name).apply()
    }

    fun getName(): String? {
        return pref.getString(NAME_KEY, null)
    }

    fun saveImage(image: String) {
        pref.edit().putString(IMAGE_URL_KEY, image).apply()
    }

    fun getImage(): String {
        return pref.getString(IMAGE_URL_KEY, "").toString()
    }

    companion object {
        const val PREF_NAME = "pref.name"
        const val SHOWED_KEY = "seen.key"
        const val NAME_KEY = "name.key"
        const val IMAGE_URL_KEY = "image.uri.key"
    }
}




