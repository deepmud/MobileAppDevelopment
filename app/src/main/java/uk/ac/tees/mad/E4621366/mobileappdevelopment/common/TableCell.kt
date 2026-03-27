package uk.ac.tees.mad.E4621366.mobileappdevelopment.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.Cell(text: String?,width: Dp) {
    Text(
        text = text ?: "-",
        modifier = Modifier
            .width(width)
            .padding(horizontal = 8.dp),
        maxLines = Int.MAX_VALUE,      // ✅ word wrap
        softWrap = true
    )
}

@Composable
fun RowScope.HeaderCell(text: String,width: Dp) {
    Text(
        text = text,
        modifier = Modifier.width(width)
            .padding(horizontal = 8.dp),
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        softWrap = false,
        overflow = TextOverflow.Clip,
    )
}
