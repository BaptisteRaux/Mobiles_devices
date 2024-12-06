package com.android.tictactoe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import androidx.work.OneTimeWorkRequestBuilder
import android.content.Intent
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testNotification()
        scheduleNotification()
        supportActionBar?.hide()
        val playButton = findViewById<Button>(R.id.button_play)

        playButton.setOnClickListener {
            val intent = Intent(this, ModeSelectionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun testNotification() {
        val testRequest = OneTimeWorkRequestBuilder<NotificationWorker>().build()
        WorkManager.getInstance(this).enqueue(testRequest)
    }

    private fun scheduleNotification() {
        val scheduleRequest = PeriodicWorkRequestBuilder<NotificationWorker>(12, TimeUnit.HOURS).build()
        WorkManager.getInstance(this).enqueue(scheduleRequest)
    }
}