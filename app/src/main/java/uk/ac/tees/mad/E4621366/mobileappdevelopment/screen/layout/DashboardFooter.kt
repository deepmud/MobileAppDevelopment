package  uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.layout


import android.graphics.Color.blue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun DashboardFooter(location:  String?) {

    Box(
        Modifier.fillMaxWidth()
            .height(100.dp) // fixed header height
            .background(Color(0xFF1976D2)) .
            navigationBarsPadding()
            .padding(vertical  = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Location: $location",
            fontSize = 14.sp,
            color = Color.White
        )
    }

}



//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Color(0xFF1976D2))
//            .navigationBarsPadding()
//
//            .padding(vertical = 12.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//
//        Text(
//            text = "Location: $location",
//            fontSize = 14.sp,
//            color = Color.White
//        )
//    }