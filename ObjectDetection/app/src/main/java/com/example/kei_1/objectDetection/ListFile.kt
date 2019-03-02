package com.example.kei_1.objectDetection

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import java.io.File

class ListFile : AppCompatActivity() {
    private var currentDir : File = Environment.getExternalStorageDirectory()
    private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_file)

        recyclerView=findViewById(R.id.filesList)
        recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        if (hasPermission()) showFiles()
    }

    private fun showFiles(){
        val adapter=FilesAdapter(this,currentDir.listFiles().toList()){file ->
            if(file.isDirectory){
                currentDir=file
                showFiles()
            }else{
                Toast.makeText(this,file.absolutePath,Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView.adapter=adapter
        title=currentDir.path
    }

    private fun hasPermission() : Boolean{
        // パーミッションを持っているか確認
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // 持っていないならパーミッションを要求
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            return false
        }

        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if (!grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showFiles()
        } else {
            finish()
        }
    }

    override fun onBackPressed() {
        if (currentDir != Environment.getExternalStorageDirectory()) {
            currentDir = currentDir.parentFile
            showFiles()
        } else {
            super.onBackPressed()
        }
    }
}
