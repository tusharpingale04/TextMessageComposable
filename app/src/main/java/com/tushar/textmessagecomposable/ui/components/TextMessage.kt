package com.tushar.textmessagecomposable.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.tushar.textmessagecomposable.ui.theme.BubbleColor

@Composable
fun TextMessage(
    modifier: Modifier = Modifier,
    message: String,
    time: String
) {
    val textMessageDimens = remember {
        TextMessageDimens()
    }
    //Content
    val content = @Composable {
        val onTextLayout: (TextLayoutResult) -> Unit = { textLayoutResult: TextLayoutResult ->
            textMessageDimens.apply {
                this.message = message
                this.messageWidth = textLayoutResult.size.width
                this.lineCount = textLayoutResult.lineCount
                this.lastLineWidth = textLayoutResult.getLineRight(textLayoutResult.lineCount - 1)
            }
        }
        //Message Composable
        Message(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
            message = message,
            onTextLayout = onTextLayout
        )
        //Time Composable
        Time(modifier = Modifier.padding(start = 4.dp, end = 8.dp, bottom = 4.dp), time = time)
    }
    //Custom Layout [This does the magic]
    CustomTextMessageLayout(content = content, modifier = modifier, textMessageDimens = textMessageDimens)
}

@Composable
internal fun CustomTextMessageLayout(
    content: @Composable () -> Unit,
    modifier: Modifier,
    textMessageDimens: TextMessageDimens
) {
    Layout(content = content, modifier = modifier) { measurables: List<Measurable>, constraints: Constraints ->
        //As discussed above, the measurables measures itself and calculates its size and placement instructions
        val placeables: List<Placeable> = measurables.map { measurable ->
            //Measuring max available width
            measurable.measure(Constraints(0, constraints.maxWidth))
        }
        //check if the message and time composable are measured, else throw IllegalArgumentException exception
        require(placeables.size == 2)

        //Placeables are the measured childrens which has its own size and placement instructions
        val message = placeables.first()
        val time = placeables.last()

        //Calculation how big the layout should be {Height and Width of Layout}
        textMessageDimens.parentWidth = constraints.maxWidth
        val padding = (message.measuredWidth - textMessageDimens.messageWidth) / 2

        //This seems to be self-explanatory
        if (textMessageDimens.lineCount > 1 && textMessageDimens.lastLineWidth + time.measuredWidth >= textMessageDimens.messageWidth + padding) {
            textMessageDimens.rowWidth = message.measuredWidth
            textMessageDimens.rowHeight = message.measuredHeight + time.measuredHeight
        } else if (textMessageDimens.lineCount > 1 && textMessageDimens.lastLineWidth + time.measuredWidth < textMessageDimens.messageWidth + padding) {
            textMessageDimens.rowWidth = message.measuredWidth
            textMessageDimens.rowHeight = message.measuredHeight
        } else if (textMessageDimens.lineCount == 1 && message.measuredWidth + time.measuredWidth >= textMessageDimens.parentWidth) {
            textMessageDimens.rowWidth = message.measuredWidth
            textMessageDimens.rowHeight = message.measuredHeight + time.measuredHeight
        } else {
            textMessageDimens.rowWidth = message.measuredWidth + time.measuredWidth
            textMessageDimens.rowHeight = message.measuredHeight
        }
        //Setting max width of the layout
        textMessageDimens.parentWidth = textMessageDimens.rowWidth.coerceAtLeast(minimumValue = constraints.minWidth)
        
        layout(width = textMessageDimens.parentWidth, height = textMessageDimens.rowHeight) {
            //Place message at (0,0) since we don't need to place it dynamically
            message.placeRelative(0, 0)
            //Place time using (maxAvailableWidth - timeWidth, messageHeight - timeHeight)
            time.placeRelative(
                textMessageDimens.parentWidth - time.width,
                textMessageDimens.rowHeight - time.height
            )
        }
    }
}

@Composable
internal fun Message(
    modifier: Modifier = Modifier,
    message: String,
    onTextLayout: (TextLayoutResult) -> Unit = {}
) {
    Text(
        text = message,
        modifier = modifier,
        onTextLayout = onTextLayout
    )
}

@Composable
internal fun Time(
    modifier: Modifier = Modifier,
    time: String
) {
    Text(
        modifier = modifier,
        text = time
    )
}

@Preview(showBackground = true)
@Composable
fun Preview_1_TextMessage() {
    Row(
        modifier = Modifier
            .padding(start = 80.dp, end = 26.dp, top = 2.dp, bottom = 2.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.End
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(10.dp))
                .background(BubbleColor)
        ) {
            TextMessage(message = "hi how are you?", time = "12:38 am")
        }
    }

}
@Preview(showBackground = true)
@Composable
fun Preview_2_TextMessage() {
    Row(
        modifier = Modifier
            .padding(start = 80.dp, end = 26.dp, top = 2.dp, bottom = 2.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.End
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(10.dp))
                .background(BubbleColor)
        ) {
            TextMessage(message = "hi", time = "01:01 am")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_3_TextMessage() {
    Row(
        modifier = Modifier
            .padding(start = 80.dp, end = 26.dp, top = 2.dp, bottom = 2.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.End
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(10.dp))
                .background(BubbleColor)
        ) {
            TextMessage(message = "hi how are you. i am going to test long msg", time = "02:11 am")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_4_TextMessage() {
    Row(
        modifier = Modifier
            .padding(start = 80.dp, end = 26.dp, top = 2.dp, bottom = 2.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.End
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .wrapContentSize()
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