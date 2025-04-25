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
import br.com.akgs.doevida.ui.donation.RequestDonationAction
import br.com.akgs.doevida.ui.donation.RequestDonationScreen
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
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
                    bottomBar = {
                        if (auth.currentUser != null) {
                            BottomNavigationBar(navController = navController)
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        navController = navController,
                        startDestination = if (auth.currentUser != null) "home" else "login",
                    ) {
                        composable("home") {
                            HomeScreen(
                                onAction = { action ->
                                    when (action) {


                                        is HomeAction.NavigateToSolicitation -> {
                                            navController.navigate("solicitation")
                                        }

                                        else -> {}
                                    }
                                },
                            )
                        }
                        composable("request_donation") {
                             RequestDonationScreen(
                                onAction = { action ->
                                    when (action) {
                                        is RequestDonationAction.OnSaveClick -> {
                                            navController.navigate("home"){
                                                popUpTo("request_donation") { inclusive = true }
                                            }
                                        }

                                        else -> {}
                                    }
                                },
                             )
                        }
                        composable("login") {
                            LoginScreen(
                                state = LoginState(),
                                onAction = { action ->
                                    when (action) {
                                        is LoginAction.OnLoginClick -> {
                                            navController.navigate("home")
                                        }

                                        is LoginAction.OnNewRegisterClick -> {
                                            navController.navigate("register")
                                        }

                                        else -> {}
                                    }
                                }
                            )
                        }
                        composable("register") {
                            RegisterScreen(
                                onAction = { action ->
                                    when (action) {
                                        is RegisterAction.OnRegisterClick -> {
                                            navController.navigate("login") {
                                                popUpTo("register") { inclusive = true }
                                            }
                                        }

                                        else -> {}
                                    }
                                },
                            )
                        }
                        composable("solicitation") {
                            SolicitationScreen(

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