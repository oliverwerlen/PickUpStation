package com.example.jtipickup.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.system.Os.remove
import com.example.jtipickup.R
import com.example.jtipickup.response.PickUpResponse
import com.google.gson.Gson

class SessionManager (context: Context){
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val PICKUP_SELECTED = "pickup_selected"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Function to save pickupstation token
     */
    fun savePickUpSelected(token: PickUpResponse) {
        val editor = prefs.edit()
        val gson = Gson()
        editor.putString(PICKUP_SELECTED, gson.toJson(token))
        editor.apply()
    }
    /**
     * Function to fetch auth token
     */
    fun fetchPickUpSelected(): PickUpResponse? {
        val gson = Gson()
        return gson.fromJson(prefs.getString(PICKUP_SELECTED, null), PickUpResponse::class.java)
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    /**
     * Function to fetch auth token
     */

    fun deleteAuthToken(){
        prefs.edit().clear().apply()
    }
}