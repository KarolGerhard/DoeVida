package br.com.akgs.doevida.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.akgs.doevida.ui.donation.requestDonation.RequestDonationAction
import br.com.akgs.doevida.ui.donation.requestDonation.RequestDonationScreen
import br.com.akgs.doevida.ui.home.HomeAction
import br.com.akgs.doevida.ui.home.HomeScreen
import br.com.akgs.doevida.ui.login.LoginAction
import br.com.akgs.doevida.ui.login.LoginScreen
import br.com.akgs.doevida.ui.profile.ProfileAction
import br.com.akgs.doevida.ui.profile.ProfileScreen
import br.com.akgs.doevida.ui.register.RegisterAction
import br.com.akgs.doevida.ui.register.RegisterScreen
import br.com.akgs.doevida.ui.splash.SplashAction
import br.com.akgs.doevida.ui.splash.SplashScreen


@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToAuth = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )

        }
        composable(Screen.Login.route) {
            LoginScreen(
                onAction = { action ->
                    when (action) {
                        is LoginAction.OnLoginClick -> {
                            Log.d("LoginScreen", "Login button clicked")
                            navController.navigate(Screen.Home.route){
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }

                        is LoginAction.OnNewRegisterClick -> {
                            navController.navigate(Screen.Register.route)
                        }

                        else -> {
                            Log.d("LoginScreen", "Unhandled action: $action")
                        }
                    }
                }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onAction = { action ->
                    when (action) {
                        is RegisterAction.OnRegisterClick -> {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Register.route) { inclusive = true }
                            }
                        }

                        else -> {
                            // Handle other actions if needed
                        }
                    }
                }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                onAction = { action ->
                    when (action) {
                        is HomeAction.NavigateToSolicitation -> {
                            navController.navigate(Screen.Solicitation.route)
                        }

                        else -> {
                            // Handle other actions if needed
                        }
                    }
                }
            )
        }
        composable(Screen.RequestDonation.route) {
            RequestDonationScreen(
                onAction = { action ->
                    when (action) {
                        is RequestDonationAction.OnSaveClick -> {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.RequestDonation.route) { inclusive = true }
                            }
                        }

                        else -> {
                            // Handle other actions if needed
                        }
                    }
                }
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                onAction = { action ->
                    when (action) {
                        is ProfileAction.OnSave -> {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Profile.route) { inclusive = true }
                            }
                        }
                        is ProfileAction.OnLougout -> {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Home.route) { inclusive = true }
                            }
                        }

                        else -> {
                            // Handle other actions if needed
                        }
                    }
                }
            )
        }
    }
}