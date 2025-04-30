package br.com.akgs.doevida.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.akgs.doevida.R
import br.com.akgs.doevida.ui.login.LoginAction
import br.com.akgs.doevida.ui.login.LoginViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToAuth: () -> Unit
) {

    val viewModel = koinViewModel<SplashScreenViewModel>()
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.actions.collect { action ->
            when (action) {
                SplashAction.OnNavigateToHome -> onNavigateToHome()
                SplashAction.OnNavigateToAuth -> onNavigateToAuth()
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF95313B)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo3),
            contentDescription = null,
            modifier = Modifier
                .size(400.dp)
        )
        if (uiState.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp)
            )
        } else if (uiState.value.error != null) {
            Text(text = "Error: ${uiState.value.error}")
        }
    }
}

@Composable
@Preview
fun SplashScreenPreview() {
    // Preview of the SplashScreen
    SplashScreen(
        onNavigateToHome = {},
        onNavigateToAuth = {}
    )
}