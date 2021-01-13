package com.poisondart.showcase.blockparty

import android.content.Context

class HighScoreManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("block_party", Context.MODE_PRIVATE)
    private var highScore = sharedPreferences.getInt("high_score", 0)

    fun getHighScore() = highScore

    fun checkNewScore(newScore: Int) {
        if (newScore > highScore) {
            highScore = newScore
            sharedPreferences
                    .edit()
                    .putInt("high_score", highScore)
                    .apply()
        }
    }
}