package uk.ac.tees.mad.E4621366.mobileappdevelopment.component

import android.Manifest
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.lazy.LazyListScope

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import uk.ac.tees.mad.E4621366.mobileappdevelopment.model.DashboardMenuItem


fun LazyListScope.dashboardMenuGrid(
    navController: NavController,
    cameraPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>
) {

    val dashboardMenuItems = listOf(
        DashboardMenuItem(
            title = "Capture",
            icon = Icons.Default.AccountBox,
            action = { cameraPermissionLauncher.launch(Manifest.permission.CAMERA) }
        ),
        DashboardMenuItem(
            title = "Indicators",
            icon = Icons.Default.Search,
            route = "indicator_page"
        )
    )
    items(dashboardMenuItems) { item ->

        MenuCard(
            item = item,
            navController = navController,
            modifier = Modifier
                .fillMaxWidth().height(150.dp)
                .padding(horizontal = 12.dp),
            onClick = (item.action ?: { item.route?.let { navController.navigate(it) } }) as (() -> Unit)?
        )

       Spacer(modifier = Modifier.height(12.dp))
    }
}

