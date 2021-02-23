package com.poisondart.showcase.core

import android.content.Context
import android.graphics.*
import java.io.IOException


class SpriteSet(context: Context, path: String) {

    private lateinit var spriteSet: Bitmap

    init {
        try {
            val inputStream = context.assets.open(path)
            spriteSet = BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun test(x: Int, y: Int, width: Int, height: Int, rect: Rect): Bitmap {
        val bmp = Bitmap.createBitmap(spriteSet, x, y, width, height)
        return Bitmap.createScaledBitmap(bmp, rect.width(), rect.height(), false)
    }
}