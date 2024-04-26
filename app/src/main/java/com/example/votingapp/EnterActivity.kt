package com.example.votingapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EnterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_enter)

        val admin = findViewById<Button>(R.id.button4)
        val videoView: VideoView = findViewById(R.id.videoView)
        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.videomain)
        videoView.setVideoURI(videoUri)
        videoView.start()
        videoView.setOnCompletionListener {
            videoView.start()
        }

        val login = findViewById<Button>(R.id.button)

        login.setOnClickListener {
            intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        admin.setOnClickListener {
            intent = Intent(this,AdminPage::class.java)
            startActivity(intent)
        }

    }
}