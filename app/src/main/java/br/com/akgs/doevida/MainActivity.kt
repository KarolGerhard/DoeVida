package br.com.akgs.doevida

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.akgs.doevida.infra.Routes
import br.com.akgs.doevida.ui.donation.requestDonation.RequestDonationAction
import br.com.akgs.doevida.ui.donation.requestDonation.RequestDonationScreen
import br.com.akgs.doevida.ui.donation.SolicitationScreen
import br.com.akgs.doevida.ui.home.HomeAction
import br.com.akgs.doevida.ui.home.HomeScreen
import br.com.akgs.doevida.ui.login.LoginAction
import br.com.akgs.doevida.ui.login.LoginScreen
import br.com.akgs.doevida.ui.login.LoginState
import br.com.akgs.doevida.ui.navigation.BottomNavigationBar
import br.com.akgs.doevida.ui.register.RegisterAction
import br.com.akgs.doevida.ui.register.RegisterScreen
import br.com.akgs.doevida.ui.theme.DoeVidaTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoeVidaTheme {
                val navController = rememberNavController()
                val auth = FirebaseAuth.getInstance()

                Scaffold (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    bottomBar = {
                        val currentRoute = navController.currentBackStackEntry?.destination?.route
                        if(currentRoute != Routes.LOGIN && currentRoute != Routes.REGISTER) {
                            BottomNavigationBar(navController = navController)
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        navController = navController,
                        startDestination = Routes.LOGIN
//                        startDestination = if (auth.currentUser != null) Routes.HOME else Routes.LOGIN

                    ) {
                        composable(Routes.HOME) {
                            HomeScreen(
                                onAction = { action ->
                                    when (action) {
                                        is HomeAction.NavigateToSolicitation -> navController.navigate(Routes.SOLICITATION)
                                    }
                                }
                            )
                        }
                        composable(Routes.LOGIN) {
                            LoginScreen(
                                state = LoginState(),
                                onAction = { action ->
                                    when (action) {
                                         LoginAction.OnLoginClick -> {
                                             navController.navigate(Routes.HOME){
                                                    popUpTo(Routes.LOGIN) { inclusive = true }
                                             }
                                         }
                                         LoginAction.OnNewRegisterClick -> {
                                             navController.navigate(Routes.REGISTER)
                                         }
                                        else -> {}
                                    }
                                }
                            )
                        }
                        composable(Routes.REGISTER) {
                            RegisterScreen(
                                onAction = { action ->
                                    when (action){
                                        RegisterAction.OnRegisterSuccess -> {
                                            navController.navigate(Routes.HOME){
                                                popUpTo(Routes.LOGIN) { inclusive = true }
                                            }
                                        }
                                    }
                                }
                            )
                        }
                        composable(Routes.SOLICITATION) { SolicitationScreen() }
                        composable(Routes.REQUEST_DONATION) {
                            RequestDonationScreen(
                                onAction = { action ->
                                    if (action is RequestDonationAction.OnSaveSuccess) {
                                        navController.navigate(Routes.HOME) {
                                            popUpTo(Routes.REQUEST_DONATION) { inclusive = true }
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

    }


    companion object {
        private const val RC_SIGN_IN = 9001
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DoeVidaTheme {
        Greeting("Android")
    }
}


@Composable
fun AuthOrMainScreen(auth: FirebaseAuth) {
    var user by remember { mutableStateOf(auth.currentUser) }

    if (user == null) {
        LoginScreen(
            onAction = {},
            state = LoginState()

        )
    } else {
        HomeScreen(
            onAction = {}
        )
    }
}