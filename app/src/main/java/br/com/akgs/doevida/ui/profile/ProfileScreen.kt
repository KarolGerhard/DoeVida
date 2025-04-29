package br.com.akgs.doevida.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.akgs.doevida.infra.Routes
import br.com.akgs.doevida.ui.login.LoginAction
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    onAction: (ProfileAction) -> Unit = {},
) {
    val viewModel = koinViewModel<ProfileViewModel>()
    val actions = viewModel.actions

    LaunchedEffect(Unit) {
        actions.collect { action ->
            onAction(action)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Suas informações",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(16.dp),
            color = Color(0xFF95313B)
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HorizontalDivider(
                color = Color(0xFF95313B),
                thickness = 1.dp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.onAction(
                            ProfileAction.NavigateToInformacion
                        )
                    },
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFDFDFD)
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color(0xFF95313B))
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    text = "Meus dados",
                    style = TextStyle(
                        color = Color(0xFF95313B),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.onAction(
                            ProfileAction.NavigateToMyDonation
                        )
                    },
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFDFDFD)
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color(0xFF95313B))
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    text = "Minhas doações",
                    style = TextStyle(
                        color = Color(0xFF95313B),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { },
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFDFDFD)
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color(0xFF95313B))
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    text = "Termos de uso",
                    style = TextStyle(
                        color = Color(0xFF95313B),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                )
            }
            Spacer(modifier = Modifier.height(64.dp))
            Button(
                onClick = {
                    viewModel.onAction(
                        ProfileAction.OnLougout
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF690714),
                    contentColor = Color(0xFFFFFFFF)
                ),

                ) {
                Text(
                    "Sair",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

        }


    }

}

@Composable
@Preview(showBackground = true)
fun ProfileScreenPreview() {
    ProfileScreen(
        onAction = {}
    )
}

