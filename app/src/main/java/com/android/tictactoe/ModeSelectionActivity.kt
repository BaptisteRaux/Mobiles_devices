package com.android.tictactoe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class ModeSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode_selection)

        val bo1Button = findViewById<Button>(R.id.bo1_button)
        val bo3Button = findViewById<Button>(R.id.bo3_button)
        val bo5Button = findViewById<Button>(R.id.bo5_button)
        supportActionBar?.hide()

        val backToMenuButton = findViewById<ImageButton>(R.id.back_to_menu_button)
        backToMenuButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        bo1Button.setOnClickListener {
            startGame(1)
        }
        bo3Button.setOnClickListener {
            startGame(3)
        }
        bo5Button.setOnClickListener {
            startGame(5)
        }
    }

    private fun startGame(bestOf: Int) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("PLAYER_1_NAME", "Player 1")
        intent.putExtra("PLAYER_2_NAME", "Player 2")
        intent.putExtra("BEST_OF", bestOf)
        startActivity(intent)
        finish()
    }
}