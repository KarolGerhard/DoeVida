package br.com.akgs.doevida.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.akgs.doevida.R

@Composable
fun LoginScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFF1F1))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = com.google.firebase.appcheck.interop.R.drawable.common_google_signin_btn_text_dark),
                contentDescription = "Doe Vida Logo",
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { /* Lógica de login com Google */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
//                colors = ButtonDefaults.buttonColors(
//                    backgroundColor = Color(0xFF4285F4),
//                    contentColor = Color.White
//                )
            ) {
                Icon(
                    painter = painterResource(id = com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_light),
                    contentDescription = "Google Logo",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Entre com sua conta Google")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* Lógica de login com Facebook */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),

            ) {
                Icon(
                    painter = painterResource(id = com.google.firebase.database.collection.R.drawable.common_google_signin_btn_icon_light_normal),
                    contentDescription = "Facebook Logo",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Entre com Facebook")
            }
        }
    }
}

@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreen()
}
