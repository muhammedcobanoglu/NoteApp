package com.muhammed.noteapp.util

import android.content.Context
import android.content.res.ColorStateList
import android.util.DisplayMetrics
import android.view.View
import androidx.annotation.ColorInt
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.setRoundedBackground(@ColorInt color: Int, context: Context, topLeft: Float?= 0F, topRight: Float?= 0F, bottomLeft: Float?= 0F, bottomRight: Float?= 0F) {
    val backgroundShapeModel: ShapeAppearanceModel = ShapeAppearanceModel.builder()
        .setTopLeftCorner(CornerFamily.ROUNDED, dpToPx(context, topLeft?:0F))
        .setTopRightCorner(CornerFamily.ROUNDED, dpToPx(context, topRight?:0F))
        .setBottomLeftCorner(CornerFamily.ROUNDED, dpToPx(context, bottomLeft?:0F))
        .setBottomRightCorner(CornerFamily.ROUNDED, dpToPx(context, bottomRight?:0F))
        .build()
    this.background = MaterialShapeDrawable(backgroundShapeModel).apply {
        fillColor = ColorStateList.valueOf(color)
    }
}

fun dpToPx(context: Context, dp: Float): Float {
    return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}