package com.poisondart.showcase.blockparty

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class BlockPartyActivity : AppCompatActivity() {

    private lateinit var blockPartyGameView: BlockPartyGameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        blockPartyGameView = BlockPartyGameView(this, size.x, size.y)
        setContentView(blockPartyGameView)
    }

    override fun onPause() {
        super.onPause()
        blockPartyGameView.pause()
    }

    override fun onResume() {
        super.onResume()
        blockPartyGameView.resume()
    }
}