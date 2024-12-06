package com.android.tictactoe

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WinnerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winner)

        val winner = intent.getStringExtra("WINNER") ?: "Unknown"
        val finalScore = intent.getStringExtra("FINAL_SCORE") ?: "0-0"

        val winnerTextView = findViewById<TextView>(R.id.winnerText)
        winnerTextView.text = "$winner WINS $finalScore"

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}