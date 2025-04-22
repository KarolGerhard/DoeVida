package br.com.akgs.doevida.ui.splash

import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.akgs.doevida.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navigateToAuthOrMainScreen: () -> Unit) {

    var rotationState by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(true) {
        delay(2000)
        navigateToAuthOrMainScreen()
    }

    LaunchedEffect(rotationState) {
        while (true) {
            delay(16)
            rotationState += 1f
        }
    }

    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = TweenSpec(durationMillis = 500), label = ""
    )


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
                .scale(scale)
                .rotate(rotationState) // Apply the rotation effect
        )
    }
}

@Composable
@Preview
fun SplashScreenPreview() {
    // Preview of the SplashScreen
    SplashScreen(
        navigateToAuthOrMainScreen = {}
    )
}