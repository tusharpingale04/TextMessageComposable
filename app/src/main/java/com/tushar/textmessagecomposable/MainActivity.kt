package com.tushar.textmessagecomposable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tushar.textmessagecomposable.ui.components.TextMessage
import com.tushar.textmessagecomposable.ui.theme.BubbleColor
import com.tushar.textmessagecomposable.ui.theme.TextMessageComposableTheme
import com.tushar.textmessagecomposable.ui.utils.drawBubbleCorner

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextMessageComposableTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier
                            .wrapContentSize()
                            .drawBubbleCorner(
                                bubbleColor = BubbleColor,
                                cornerShape = with(LocalDensity.current) { 16.dp.toPx() },
                                arrowWidth = with(LocalDensity.current) { 8.dp.toPx() },
                                arrowHeight = with(LocalDensity.current) { 12.dp.toPx() },
                                shouldDrawArrow = true
                            )
                            .clip(RoundedCornerShape(10.dp))
                            .background(BubbleColor)
                    ) {
                        TextMessage(
                            message = "hi how are you? How are you doing? Hope you're doing good.",
                            time = "03:22 am"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TextMessageComposableTheme {
        Greeting("Android")
    }
}