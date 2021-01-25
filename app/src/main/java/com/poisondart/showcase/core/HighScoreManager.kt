package com.poisondart.showcase.core

import android.content.Context

class HighScoreManager(context: Context) {

    companion object {
        private const val SHOW_CASE_PREFERENCES = "show_case_preferences"
    }

    private val sharedPreferences = context.getSharedPreferences(SHOW_CASE_PREFERENCES, Context.MODE_PRIVATE)

    fun getHighScore(scoreTitle: String): Int {
        return sharedPreferences.getInt(scoreTitle, 0)
    }

    fun updateHighScore(scoreTitle: String, newScore: Int) {
        val oldScore = getHighScore(scoreTitle)
        if (newScore > oldScore) {
            sharedPreferences
                    .edit()
                    .putInt(scoreTitle, newScore)
                    .apply()
        }
    }

}