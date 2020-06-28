package com.example.android.clippingexample.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.View
import com.example.android.clippingexample.R

class ClippedView(context: Context) : View(context) {

    private val paint = Paint().apply {
        isAntiAlias = true
        strokeWidth = resources.getDimension(R.dimen.strokeWidth)
        textSize = resources.getDimension(R.dimen.textSize)
    }

    private val path = Path()

    private val clipRectRight = resources.getDimension(R.dimen.clipRectRight)
    private val clipRectBottom = resources.getDimension(R.dimen.clipRectBottom)
    private val clipRectTop = resources.getDimension(R.dimen.clipRectTop)
    private val clipRectLeft = resources.getDimension(R.dimen.clipRectLeft)

    private val rectInset = resources.getDimension(R.dimen.rectInset)
    private val smallRectOffset = resources.getDimension(R.dimen.smallRectOffset)

    private val circleRadius = resources.getDimension(R.dimen.circleRadius)

    private val textOffset = resources.getDimension(R.dimen.textOffset)
    private val textSize = resources.getDimension(R.dimen.textSize)

    private val columnOne = rectInset
    private val columnTwo = columnOne + rectInset + clipRectRight

    private val rowOne = rectInset
    private val rowTwo = rowOne + rectInset + clipRectBottom
    private val rowThree = rowTwo + rectInset + clipRectBottom
    private val rowFour = rowThree + rectInset + clipRectBottom
    private val textRow = rowFour + (1.5f * clipRectBottom)

    // Overrides
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawBackAndUnclippedRectangle(canvas)
        drawDifferenceClippingExample(canvas)
        drawCircularClippingExample(canvas)
        drawIntersectionClippingExample(canvas)
        drawCombinedClippingExample(canvas)
        drawRoundedRectangleClippingExample(canvas)
        drawOutsideClippingExample(canvas)
        drawSkewedTextExample(canvas)
        drawTranslatedTextExample(canvas)
        // drawQuickRejectExample(canvas)
    }


    // Helpers
    private fun drawClippedRectangle(canvas: Canvas) {
        canvas.apply {
            // Clip canvas
            clipRect(
                clipRectLeft,
                clipRectTop,
                clipRectRight,
                clipRectBottom
            )

            // Paint white
            drawColor(Color.WHITE)

            // Paint red line
            paint.color = Color.RED
            drawLine(
                clipRectLeft,
                clipRectTop,
                clipRectRight,
                clipRectBottom,
                paint
            )

            // Paint green circle
            paint.color = Color.GREEN
            drawCircle(
                circleRadius,
                clipRectBottom - circleRadius,
                circleRadius,
                paint
            )

            // Paint blue text
            paint.color = Color.BLUE
            paint.textSize = textSize
            paint.textAlign = Paint.Align.RIGHT
            drawText(
                context.getString(R.string.clipping),
                clipRectRight,textOffset,paint
            )

        }





    }

    private fun drawBackAndUnclippedRectangle(canvas: Canvas) {
        canvas.apply {
            // Draw entire canvas grey
            drawColor(Color.GRAY)
            save()

            // Translate global -> local
            translate(columnOne,rowOne)

            // Draw rectangle
            drawClippedRectangle(canvas)

            // Restore canvas to previous state
            restore()
        }

    }
    private fun drawDifferenceClippingExample(canvas: Canvas) {
    }
    private fun drawCircularClippingExample(canvas: Canvas) {
    }
    private fun drawIntersectionClippingExample(canvas: Canvas) {
    }
    private fun drawCombinedClippingExample(canvas: Canvas) {
    }
    private fun drawRoundedRectangleClippingExample(canvas: Canvas) {
    }
    private fun drawOutsideClippingExample(canvas: Canvas) {
    }
    private fun drawTranslatedTextExample(canvas: Canvas) {
    }
    private fun drawSkewedTextExample(canvas: Canvas) {
    }
    private fun drawQuickRejectExample(canvas: Canvas) {
    }
}