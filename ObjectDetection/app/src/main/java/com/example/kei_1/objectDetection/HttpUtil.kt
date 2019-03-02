package com.example.kei_1.objectDetection

import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import okhttp3.RequestBody




class HttpUtil {
    fun httpGET(url : String): String? {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(url)
                .build()

        val response = client.newCall(request).execute()
        return response.body()?.string()
    }

    fun httpPOSTimage(url : String , path : String): String? {
        val client = OkHttpClient()
        val imageFile= File(path)

        val requestBody = RequestBody.create(MediaType.parse("image/jpg"), imageFile)

        val request=Request.Builder().url(url).post(requestBody).build()

        val response = client.newCall(request).execute()
        return response.body()?.string()
    }
}