package com.example.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var tv: TextView
    lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         tv = findViewById(R.id.tv)
         button = findViewById(R.id.button)
        button.setOnClickListener {
           api()
        }
    }

    fun api() {
 CoroutineScope(Dispatchers.IO).launch {
                var data = async {
                    fetchadvice()
                }.await()
                if (data.isNotEmpty()) {
                    updatetext(data)
                }
            }
   }
    fun fetchadvice(): String {
        var rsponse = ""
        try {
            rsponse = URL("https://api.adviceslip.com/advice").readText(Charsets.UTF_8)
        } catch (e: Exception) {
            println("error")
        }
        return rsponse
    }

    suspend fun updatetext(data:String)
    {
       var advices=ArrayList<String>()

        withContext(Dispatchers.Main)
    {

        val jsonObject= JSONObject(data)
        val slip=jsonObject.getJSONObject("slip")
        val newone=slip.getString("advice")
//
        tv.text=newone
   }
    }

}