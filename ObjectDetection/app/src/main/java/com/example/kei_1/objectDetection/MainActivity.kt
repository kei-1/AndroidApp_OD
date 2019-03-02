package com.example.kei_1.objectDetection

import android.os.Bundle
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
    val URL = "http://10.0.2.2:3000/posts/1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getButton = findViewById(R.id.button) as Button
        getButton.setOnClickListener(object : View.OnClickListener {
            override
            fun onClick(view: View) {
                onParallelGetButtonClick()
            }
        })
    }

    fun onParallelGetButtonClick() = GlobalScope.launch(Dispatchers.Main) {
        val http = HttpUtil()
        val mapper = jacksonObjectMapper()
        async(Dispatchers.Default) { http.httpGET1(URL) }.await().let {

            val textView = findViewById(R.id.text) as TextView
            if(it != null) {
                val post = mapper.readValue<Posts>(it)
                textView.setText(post.author)
            }
        }
    }
}