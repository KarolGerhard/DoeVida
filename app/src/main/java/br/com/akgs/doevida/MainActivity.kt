package br.com.akgs.doevida

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.akgs.doevida.ui.navigation.BottomNavigationBar
import br.com.akgs.doevida.ui.navigation.NavGraph
import br.com.akgs.doevida.ui.navigation.Screen
import br.com.akgs.doevida.ui.theme.DoeVidaTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoeVidaTheme {
                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry?.destination?.route
                val showBottomBar = currentRoute in Screen.bottomBar.map { it.route }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavigationBar(navController)
                        }
                    }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = androidx.compose.material3.MaterialTheme.colorScheme.background
                    ) {
                        NavGraph(
                            navController = navController,
                            startDestination = Screen.Splash.route,
                        )
                    }
                }
            }
        }
    }
}
