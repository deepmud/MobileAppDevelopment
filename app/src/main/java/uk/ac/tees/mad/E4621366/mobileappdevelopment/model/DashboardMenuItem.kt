package uk.ac.tees.mad.E4621366.mobileappdevelopment.model



import androidx.compose.ui.graphics.vector.ImageVector

data class DashboardMenuItem(
    val title: String,
    val icon: ImageVector,
//    val route: String

    val route: String? = null,
    val action: (() -> Unit)? = null
)
