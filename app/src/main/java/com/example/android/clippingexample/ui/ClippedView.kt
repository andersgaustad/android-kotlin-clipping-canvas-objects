package com.example.android.clippingexample.ui

import android.content.Context
import android.graphics.*
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

    private var rectF = RectF(
        rectInset,
        rectInset,
        clipRectRight - rectInset,
        clipRectBottom - rectInset
    )

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
        canvas.apply {
            // Save
            save()

            // Translate
            translate(columnTwo, rowOne)

            // Cut out rectangles
            canvas.clipRect(
                2 * rectInset,
                2 * rectInset,
                clipRectRight - 2 * rectInset,
                clipRectBottom - 2 * rectInset
            )

            canvas.clipRect(
                4 * rectInset,
                4 * rectInset,
                clipRectRight - 4 * rectInset,
                clipRectBottom - 4 * rectInset,
                Region.Op.DIFFERENCE
            )
            // For API >= 26
            /*
            val length = 4
            clipOutRect(
                length * rectInset,
                length * rectInset,
                clipRectRight - length * rectInset,
                clipRectBottom - length * rectInset)

             */

            drawClippedRectangle(this)

            // Restore
            restore()
        }
    }

    private fun drawCircularClippingExample(canvas: Canvas) {
        canvas.apply {
            save()
            translate(columnOne, rowTwo)

            path.rewind()
            path.addCircle(
                circleRadius,
                clipRectBottom - circleRadius,
                circleRadius,
                Path.Direction.CCW
            )
            clipPath(path, Region.Op.DIFFERENCE)

            drawClippedRectangle(this)

            canvas.restore()

        }
    }

    private fun drawIntersectionClippingExample(canvas: Canvas) {
        canvas.apply {
            save()
            translate(columnTwo,rowTwo)

            clipRect(
                clipRectLeft,
                clipRectTop,
                clipRectRight - smallRectOffset,
                clipRectBottom - smallRectOffset
            )

            clipRect(
                clipRectLeft + smallRectOffset,
                clipRectTop + smallRectOffset,
                clipRectRight,
                clipRectBottom,
                Region.Op.INTERSECT
            )

            drawClippedRectangle(this)
            restore()
        }


    }

    private fun drawCombinedClippingExample(canvas: Canvas) {
        canvas.apply {
            save()
            translate(columnOne, rowThree)

            path.rewind()
            path.addCircle(
                clipRectLeft + rectInset + circleRadius,
                clipRectTop + circleRadius + rectInset,
                circleRadius,Path.Direction.CCW
            )
            path.addRect(
                clipRectRight / 2 - circleRadius,
                clipRectTop + circleRadius + rectInset,
                clipRectRight / 2 + circleRadius,
                clipRectBottom - rectInset,Path.Direction.CCW
            )

            clipPath(path)

            drawClippedRectangle(this)
            restore()
        }

    }

    private fun drawRoundedRectangleClippingExample(canvas: Canvas) {
        canvas.apply {
            save()
            translate(columnTwo,rowThree)

            path.rewind()
            path.addRoundRect(
                rectF,
                clipRectRight / 4,
                clipRectRight / 4,
                Path.Direction.CCW
            )

            canvas.clipPath(path)
            drawClippedRectangle(canvas)
            canvas.restore()
        }
    }

    private fun drawOutsideClippingExample(canvas: Canvas) {
        canvas.apply {
            save()

            translate(columnOne,rowFour)

            clipRect(2 * rectInset,
                2 * rectInset,
                clipRectRight - 2 * rectInset,
                clipRectBottom - 2 * rectInset
            )

            drawClippedRectangle(canvas)

            canvas.restore()
        }
    }

    private fun drawTranslatedTextExample(canvas: Canvas) {
        canvas.apply {
            save()

            paint.color = Color.GREEN
            // Align the RIGHT side of the text with the origin.
            paint.textAlign = Paint.Align.LEFT
            // Apply transformation to canvas.
            translate(columnTwo,textRow)
            // Draw text.
            drawText(
                context.getString(R.string.translated),
                clipRectLeft,
                clipRectTop,
                paint)
            restore()
        }
    }

    private fun drawSkewedTextExample(canvas: Canvas) {
        canvas.apply {
            save()

            paint.color = Color.YELLOW
            paint.textAlign = Paint.Align.RIGHT

            // Position text.
            translate(columnTwo, textRow)

            // Apply skew transformation.
            skew(0.2f, 0.3f)

            drawText(
                context.getString(R.string.skewed),
                clipRectLeft,
                clipRectTop,
                paint
            )

            restore()
        }
    }

}