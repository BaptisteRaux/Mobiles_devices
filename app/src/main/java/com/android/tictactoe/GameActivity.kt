package com.android.tictactoe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import android.widget.ImageButton

class GameActivity : AppCompatActivity() {
    private var player1Name = "Player 1"
    private var player2Name = "Player 2"
    private var player1Symbol = "X"
    private var player2Symbol = "O"
    private var isPlayer1Turn = true
    private var currentPlayer = "X"

    private val gameBoard = Array(3) { arrayOfNulls<Button>(3) }
    private var gameOver = false
    private var playerXScore = 0
    private var playerOScore = 0
    private var bestOf = 0

    private lateinit var statusText: TextView
    private lateinit var playerXSection: TextView
    private lateinit var playerOSection: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        player1Name = intent.getStringExtra("PLAYER_1_NAME") ?: "Player 1"
        player2Name = intent.getStringExtra("PLAYER_2_NAME") ?: "Player 2"
        bestOf = intent.getIntExtra("BEST_OF", 1)

        playerXSection = findViewById(R.id.playerXSection)
        playerOSection = findViewById(R.id.playerOSection)
        statusText = findViewById(R.id.statusText)
        updateScore()
        supportActionBar?.hide()

        val exitButton = findViewById<ImageButton>(R.id.exit_button)
        exitButton.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        gameBoard[0][0] = findViewById(R.id.button_00)
        gameBoard[0][1] = findViewById(R.id.button_01)
        gameBoard[0][2] = findViewById(R.id.button_02)
        gameBoard[1][0] = findViewById(R.id.button_10)
        gameBoard[1][1] = findViewById(R.id.button_11)
        gameBoard[1][2] = findViewById(R.id.button_12)
        gameBoard[2][0] = findViewById(R.id.button_20)
        gameBoard[2][1] = findViewById(R.id.button_21)
        gameBoard[2][2] = findViewById(R.id.button_22)

        for (i in 0 until 3) {
            for (j in 0 until 3) {
                gameBoard[i][j]?.setOnClickListener {
                    onCellClicked(it as Button, i, j)
                }
            }
        }
    }

    fun onCellClicked(button: Button, row: Int, col: Int) {
        if (gameOver || button.text.isNotEmpty()) return

        button.text = currentPlayer
        if (checkWinner()) {
            gameOver = true
            if (currentPlayer == player1Symbol) {
                playerXScore++
            } else {
                playerOScore++
            }
            updateScore()

            if (isGameFinish()) {
                statusText.text = "${if (currentPlayer == player1Symbol) player1Name else player2Name} wins the game!"
                exitToWinnerPage(if (currentPlayer == player1Symbol) player1Name else player2Name)
            } else {
                statusText.text = "${if (currentPlayer == player1Symbol) player1Name else player2Name} wins the round!"
                showRoundWinner(false)
            }

        } else if (isBoardFull()) {
            gameOver = true
            statusText.text = "It's a draw!"
            showRoundWinner(true)
        } else {
            isPlayer1Turn = !isPlayer1Turn
            currentPlayer = if (isPlayer1Turn) player1Symbol else player2Symbol
            statusText.text = if (isPlayer1Turn) {
                "$player1Name's turn"
            } else {
                "$player2Name's turn"
            }
        }
    }

    private fun isBoardFull(): Boolean {
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (gameBoard[i][j]?.text.isNullOrEmpty()) return false
            }
        }
        return true
    }

    private fun updateScore() {
        playerXSection.text = "$player1Name: $playerXScore"
        playerOSection.text = "$player2Name: $playerOScore"
    }

    private fun checkWinner(): Boolean {
        for (i in 0 until 3) {
            if (gameBoard[i][0]?.text == currentPlayer && gameBoard[i][1]?.text == currentPlayer && gameBoard[i][2]?.text == currentPlayer)
                return true
            if (gameBoard[0][i]?.text == currentPlayer && gameBoard[1][i]?.text == currentPlayer && gameBoard[2][i]?.text == currentPlayer)
                return true
        }

        if (gameBoard[0][0]?.text == currentPlayer && gameBoard[1][1]?.text == currentPlayer && gameBoard[2][2]?.text == currentPlayer)
            return true
        if (gameBoard[0][2]?.text == currentPlayer && gameBoard[1][1]?.text == currentPlayer && gameBoard[2][0]?.text == currentPlayer)
            return true
        return false
    }

    private fun isGameFinish(): Boolean {
        return when {
            bestOf == 1 -> playerXScore == 1 || playerOScore == 1
            bestOf == 3 -> playerXScore == 2 || playerOScore == 2
            bestOf == 5 -> playerXScore == 3 || playerOScore == 3
            else -> false
        }
    }

    private fun showRoundWinner(isDraw: Boolean) {
        if (isDraw) {
            Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Player $currentPlayer wins the round!", Toast.LENGTH_SHORT).show()
        }
        Handler(Looper.getMainLooper()).postDelayed({
            resetBoard()
        }, 2000)
    }

    private fun resetBoard() {
        if (player1Symbol == "X") {
            player1Symbol = "O"
            player2Symbol = "X"
        } else {
            player1Symbol = "X"
            player2Symbol = "O"
        }

        for (i in 0 until 3) {
            for (j in 0 until 3) {
                gameBoard[i][j]?.text = ""
                gameBoard[i][j]?.isEnabled = true
            }
        }

        isPlayer1Turn = true
        currentPlayer = player1Symbol
        gameOver = false
        statusText.text = "$player1Name's turn ($player1Symbol)"
    }

    private fun exitToWinnerPage(winner: String) {
        val intent = Intent(this, WinnerActivity::class.java)
        intent.putExtra("WINNER", winner)
        intent.putExtra("FINAL_SCORE", "$playerXScore-$playerOScore")
        startActivity(intent)
        finish()
    }
}