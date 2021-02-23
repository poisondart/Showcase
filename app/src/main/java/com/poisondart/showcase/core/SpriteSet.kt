package com.poisondart.showcase.core

import android.content.Context
import android.graphics.*
import java.io.IOException
import java.lang.IllegalArgumentException

class SpriteSet(context: Context, path: String) {

    private lateinit var spriteSet: Bitmap

    private val sprites = mutableMapOf<String, Bitmap>()

    init {
        try {
            val inputStream = context.assets.open(path)
            spriteSet = BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun createSprite(name: String, x: Int, y: Int, width: Int, height: Int, rect: Rect) {
        val originalBitmap = Bitmap.createBitmap(spriteSet, x, y, width, height)
        val scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, rect.width(), rect.height(), false)
        sprites[name] = scaledBitmap
    }

    fun getSprite(name: String): Bitmap {
        return sprites[name]?: throw IllegalArgumentException("This sprite is not found")
    }
}