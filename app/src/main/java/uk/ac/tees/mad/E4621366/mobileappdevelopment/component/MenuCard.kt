package uk.ac.tees.mad.E4621366.mobileappdevelopment.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import uk.ac.tees.mad.E4621366.mobileappdevelopment.model.DashboardMenuItem
@Composable
fun MenuCard(
    item: DashboardMenuItem,
    navController: NavController,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {

    Card(
        modifier = modifier
            .fillMaxWidth()            // allow Row.weight to control width
            .height(100.dp)            // fixed height for consistent cards
            .clickable { onClick?.invoke() ?: item.route?.let { navController.navigate(it) } },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = Color(0xFF1976D2),
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.title,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF0D47A1)
            )
        }
    }
}
