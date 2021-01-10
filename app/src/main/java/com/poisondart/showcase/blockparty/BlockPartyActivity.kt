package com.poisondart.showcase.blockparty

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class BlockPartyActivity : AppCompatActivity() {

    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        gameView = GameView(this, size.x, size.y)
        setContentView(gameView)
    }

    override fun onPause() {
        super.onPause()
        gameView.pause()
    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
    }
}