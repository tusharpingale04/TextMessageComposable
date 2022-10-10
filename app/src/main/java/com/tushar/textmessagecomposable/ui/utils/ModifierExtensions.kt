package com.tushar.textmessagecomposable.ui.utils

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

/**
 * @author Porush Tyagi
 * @since 08,October,2022
 */

fun Modifier.drawBubbleCorner(
    bubbleColor: Color,
    cornerShape: Float,
    arrowWidth: Float,
    arrowHeight: Float,
    shouldDrawArrow: Boolean
) = Modifier.then(
    background(
        color = bubbleColor,
        shape = RightBubbleShape(
            cornerShape = cornerShape,
            arrowWidth = arrowWidth,
            arrowHeight = arrowHeight,
            shouldDrawArrow = shouldDrawArrow
        )
    )
)

class RightBubbleShape(
    private val shouldDrawArrow: Boolean,
    private val cornerShape: Float,
    private val arrowWidth: Float,
    private val arrowHeight: Float
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(Path().apply {
            reset()
            moveTo(cornerShape, 0f)
            if (shouldDrawArrow) {
                lineTo(size.width + arrowWidth, 0f)
                arcTo(
                    rect = Rect(
                        offset = Offset(size.width + arrowWidth, 0f),
                        size = Size(10f, 10f)
                    ),
                    startAngleDegrees = 270f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
                lineTo(size.width, arrowHeight)
            } else {
                lineTo(size.width - cornerShape, 0f)
                arcTo(
                    rect = Rect(
                        Offset(x = size.width - cornerShape, y = 0f),
                        Size(width = cornerShape, height = cornerShape)
                    ),
                    startAngleDegrees = 270f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
            }
            lineTo(size.width, size.height - cornerShape)
            arcTo(
                rect = Rect(
                    offset = Offset(size.width - cornerShape, size.height - cornerShape),
                    size = Size(cornerShape, cornerShape)
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(size.width - cornerShape, size.height)
            lineTo(0f, size.height)
            arcTo(
                rect = Rect(
                    offset = Offset(0f, size.height - cornerShape),
                    size = Size(cornerShape, cornerShape)
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(0f, cornerShape)
            arcTo(
                rect = Rect(
                    offset = Offset(0f, 0f),
                    size = Size(cornerShape, cornerShape)
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            close()
        })
    }
}