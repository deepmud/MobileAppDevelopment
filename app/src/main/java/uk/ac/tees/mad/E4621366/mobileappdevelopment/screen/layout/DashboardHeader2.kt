package uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter


@Composable
fun DashboardHeader2(
    appName: String,
//    cameraPermission: () -> Unit,
//    userName: String?,
//    profileImage: String?,
    onLogout: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
           .height(80.dp) // fixed header height
            .background(Color(0xFF1976D2))
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = appName,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
                    color = Color.White,

        )
//        if (profileImage != " ") {
//            IconButton(onClick = cameraPermission){
//
//
//                Icon(
//                    //imageVector = Icons.Default.Logout,
//                    imageVector = Icons.Default.AccountBox,
//                    modifier = Modifier.size(60.dp),
//                    contentDescription = "Camera",
//                    tint = Color.White
//                )
//
////                Image(
////               painter = rememberAsyncImagePainter(profileImage),
////                contentDescription = "Profile Image",
////                modifier = Modifier
////                    .size(40.dp)
////                    .clip(CircleShape)
////            )
//
//            }
//        } else {
//            Icon(
//                imageVector = Icons.Filled.AccountCircle,
//                contentDescription = "Default Profile",
//                modifier = Modifier.size(40.dp)
//            )
//        }
//        Row(
//            verticalAlignment = Alignment.CenterVertically
//        ) {

//            Text(
//                text = if(userName != null) userName else "Unknown",
//                fontSize = 16.sp,
//                modifier = Modifier.padding(end = 8.dp),
//                color = Color.White
//            )


//            IconButton(onClick = onLogout) {
//                Icon(
//                    //imageVector = Icons.Default.Logout,
//                    imageVector = Icons.Filled.Lock,
//                    //close delete lock
//                    contentDescription = "Logout",
//                    tint = Color.White
//                )
//
//            }
//        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .clickable { onLogout() }
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = "Logout",
                tint = Color.White
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "Logout",
                fontSize = 16.sp,
                color = Color.White
            )
        }

    }
}


