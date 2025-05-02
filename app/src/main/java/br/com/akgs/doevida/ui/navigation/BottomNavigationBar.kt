package br.com.akgs.doevida.ui.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.akgs.doevida.R


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem(Screen.Home.route, "Inicio", R.drawable.ic_home),
        BottomNavItem(Screen.RequestDonation.route, "Solicitar", R.drawable.ic_add),
        BottomNavItem(Screen.Profile.route, "Perfil", R.drawable.ic_person)
    )
    NavigationBar(
        containerColor = Color(0xFF95313B),
        contentColor =  Color(0xFFFDFDFD),
        tonalElevation = 8.dp,
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .padding(0.dp)
    ) {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navController.currentBackStackEntry?.destination?.route


        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.label, tint = Color(0xFFFDFDFD)) },
                label = { Text(item.label, style = TextStyle(
                    color = Color(0xFFFDFDFD),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    val navController = rememberNavController()
    BottomNavigationBar(navController)
}

data class BottomNavItem(val route: String, val label: String, val icon: Int)
