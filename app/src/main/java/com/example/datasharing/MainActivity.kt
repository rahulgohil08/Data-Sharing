package com.example.datasharing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.datasharing.utils.SharedPrefManager

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPrefManager: SharedPrefManager
    private val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPrefManager = SharedPrefManager(context)

    }

    fun logout(view: android.view.View) {
        sharedPrefManager.clear()
        openActivity(LoginActivity::class.java)
        finish()
    }

    private fun openActivity(aclass: Class<*>) {
        val intent = Intent(context, aclass)
        startActivity(intent)
    }
}