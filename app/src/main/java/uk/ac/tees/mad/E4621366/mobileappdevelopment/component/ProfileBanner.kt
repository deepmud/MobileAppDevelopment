package uk.ac.tees.mad.E4621366.mobileappdevelopment.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter


@Composable
fun ProfileBanner(imagePath: String?,
    userName: String?,
) {

    val painter = rememberAsyncImagePainter(
        model = imagePath ?:String()
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 8.dp),

    contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = "Profile",
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .border(4.dp, Color( 0xffffffff), CircleShape),
            contentScale = ContentScale.Crop
        )

    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 8.dp),

        contentAlignment = Alignment.Center
    ) {
    Text(
        text = if(userName != null) userName else "Unknown",
        fontSize = 16.sp,
        color = Color( 0xff1e3c65)
    )}
}
