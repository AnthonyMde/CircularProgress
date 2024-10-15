package com.example.circularprogresstest

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.min

@Composable
fun CustomCircularProgress(count: Int) {
    val max = 1000
    val safeCount = min(count, max)
    val sweepAngle = (safeCount * 100) / max * 2.4
    val animatedSweep by animateFloatAsState(
        targetValue = sweepAngle.toFloat(),
        animationSpec = tween(durationMillis = 1000),
        label = "circular progression"
    )

    Box(
        Modifier
            .size(400.dp)
            .drawBehind {
                val componentSize = size / 1.25f
                val xOffSet = (size.width - componentSize.width) / 2
                val yOffSet = (size.height - componentSize.height) / 2
                drawCircularShape(
                    componentSize,
                    xOffSet,
                    yOffSet,
                    color = Color.Gray.copy(alpha = 0.1f)
                )
                drawCircularShape(
                    componentSize,
                    xOffSet,
                    yOffSet,
                    color = Color.Cyan.copy(alpha = 0.4f),
                    sweep = animatedSweep
                )
            },
        contentAlignment = Alignment.Center
    ) {
        val animatedCount by animateIntAsState(
            count,
            animationSpec = tween(1000),
            label = "counter animation"
        )
        val animatedColor by animateColorAsState(
            targetValue = if (count == 0) Color.LightGray else Color.Black,
            animationSpec = tween(1000)
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Remaining",
                style = MaterialTheme.typography.bodyLarge,
                color = animatedColor
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "$animatedCount GB",
                style = MaterialTheme.typography.displaySmall,
                color = animatedColor
            )
        }
    }
}

private fun DrawScope.drawCircularShape(
    componentSize: Size,
    xOffSet: Float,
    yOffSet: Float,
    sweep: Float = 240f,
    color: Color

) {
    drawArc(
        size = componentSize,
        color = color,
        startAngle = 150f,
        sweepAngle = sweep,
        useCenter = false,
        style = Stroke(width = 50f, cap = StrokeCap.Round),
        topLeft = Offset(x = xOffSet, y = yOffSet)
    )
}

@Composable
@Preview
fun CustomCircularProgressPreview() {
    CustomCircularProgress(500)
}