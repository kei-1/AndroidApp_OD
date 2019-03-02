package com.example.kei_1.objectDetection

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

class MainActivity : AppCompatActivity() {

    val URL = "http://10.0.2.2:3000/image"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getButton = findViewById(R.id.button) as Button

        getButton.setOnClickListener {
            val intent = Intent(this, ListFile::class.java)
            startActivity(intent)
        }
        /*
        getButton.setOnClickListener(object : View.OnClickListener {
            override
            fun onClick(view: View) {
                onParallelGetButtonClick()
            }
        })
     */
    }

    fun onParallelGetButtonClick() = GlobalScope.launch(Dispatchers.Main) {
        val http = HttpUtil()
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath

        async(Dispatchers.Default) { http.httpPOSTimage(URL,path) }.await().let {
            val textView = findViewById(R.id.text) as TextView
            val result =it
            val a ="aaa"
        }

        /*
        val mapper = jacksonObjectMapper()
        async(Dispatchers.Default) { http.httpGET(URL) }.await().let {
            val textView = findViewById(R.id.text) as TextView
            if(it != null) {
                val post = mapper.readValue<Posts>(it)
                textView.setText(post.author)
            }
        }
        */
    }
}