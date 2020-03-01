package com.deepak.coroutinesexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val resultApi = "Result Api"
    private val resultApi2 = "Result Api2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            CoroutineScope(IO).launch { fakeResult() }

        }
    }

    private fun setTextOnUi(input: String) {
        val text = tv.text.toString() + "\n$input"
        tv.text = text
    }

    private suspend fun setTextOnMainThread(input: String) {
//        CoroutineScope(Main).launch {
//            setTextOnUi(input)
//        }
        //or
        withContext(Main) {
            setTextOnUi(input)
        }

    }

    private suspend fun fakeResult() {
        val result = getResultFromApi()
        println(result)
        setTextOnMainThread(result)

        val result2 = getResult2FromApi()
        println(result2)
        setTextOnMainThread(result2)
    }

    private suspend fun getResultFromApi(): String {
        logThread("getResultFromApi")
        delay(1000)
        return resultApi
    }
    private suspend fun getResult2FromApi(): String {
        logThread("getResult2FromApi")
        delay(1000)
        return resultApi2
    }
    private fun logThread(mName: String) {
        println("debug: ${mName}:${Thread.currentThread().name}")
    }
}
