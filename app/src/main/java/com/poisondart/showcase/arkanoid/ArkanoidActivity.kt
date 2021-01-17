package com.poisondart.showcase.arkanoid

import android.graphics.Point
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ArkanoidActivity: AppCompatActivity() {
    private lateinit var arkanoidGameView: ArkanoidGameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        arkanoidGameView = ArkanoidGameView(this, size.x, size.y)
        setContentView(arkanoidGameView)
    }

    override fun onPause() {
        super.onPause()
        arkanoidGameView.pause()
    }

    override fun onResume() {
        super.onResume()
        arkanoidGameView.resume()
    }
}