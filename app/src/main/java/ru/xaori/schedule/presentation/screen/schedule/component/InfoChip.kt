package ru.xaori.schedule.presentation.screen.schedule.component

import android.R.attr.onClick
import android.R.attr.text
import android.R.attr.textColor
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun InfoChip(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    onClick: (String) -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors(
            backgroundColor
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.clickable { onClick(text) }
    ) {
        Text(
            text,
            style = MaterialTheme.typography.labelMedium,
            color = textColor,
            modifier = Modifier.padding(10.dp, 6.dp)
        )
    }
}