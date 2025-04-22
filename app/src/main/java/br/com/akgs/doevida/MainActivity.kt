package br.com.akgs.doevida

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import br.com.akgs.doevida.ui.home.HomeScreen
import br.com.akgs.doevida.ui.login.LoginAction
import br.com.akgs.doevida.ui.login.LoginScreen
import br.com.akgs.doevida.ui.login.LoginState
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                    ) {
                        composable("home") {
                            HomeScreen()
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
//            user = user!!,  // Pass the user information to MainScreen
//            onSignOut = {
//                auth.signOut()
//                user = null
//            }
        )
    }
}