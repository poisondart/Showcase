package com.poisondart.showcase.core

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class GameActivity: AppCompatActivity() {
    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        gameView = createGameView(this, size.x, size.y)
        setContentView(gameView)
    }

    abstract fun createGameView(context: Context, x: Int, y: Int): GameView

    override fun onPause() {
        super.onPause()
        gameView.pause()
    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
    }

}