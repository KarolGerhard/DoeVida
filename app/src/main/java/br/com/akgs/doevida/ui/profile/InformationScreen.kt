package br.com.akgs.doevida.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.akgs.doevida.infra.remote.entities.RequestDonation
import br.com.akgs.doevida.infra.remote.entities.User
import br.com.akgs.doevida.ui.donation.SolicitationAction
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationScreen(
//    onAction: (SolicitationAction) -> Unit = {},
//    user: User
) {
    val viewModel = koinViewModel<ProfileViewModel>()
    val user = viewModel.uiState.collectAsState().value



    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Tipo sanguineo",
                style = TextStyle(
                    color = Color(0xFF6A343A),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            )
            Text(
                modifier = Modifier
                    .padding(end = 8.dp),
                text = viewModel.user.bloodType ?: "",
                style = TextStyle(
                    color = Color(0xFF95313B),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Telefone",
                style = TextStyle(
                    color = Color(0xFF6A343A),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            )
            Text(
                modifier = Modifier
                    .padding(end = 8.dp),
                text = viewModel.user.phone ?: "",
                style = TextStyle(
                    color = Color(0xFF95313B),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Email",
                style = TextStyle(
                    color = Color(0xFF6A343A),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            )
            Text(
                modifier = Modifier
                    .padding(end = 8.dp),
                text = viewModel.user.email ?: "",
                style = TextStyle(
                    color = Color(0xFF95313B),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Endereço",
                style = TextStyle(
                    color = Color(0xFF6A343A),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            )
            Text(
                modifier = Modifier
                    .padding(end = 8.dp),
                text = "${viewModel.user.city} - ${viewModel.user.state}",
                style = TextStyle(
                    color = Color(0xFF95313B),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            )

        }

    }

}

@Composable
@Preview(showBackground = true)
fun InformationScreePreview() {
    InformationScreen()
}