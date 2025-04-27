package br.com.akgs.doevida.ui.donation.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.akgs.doevida.infra.remote.entities.RequestDonation
import br.com.akgs.doevida.ui.donation.SolicitationAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequesetDonationDetailsBottomSheet(
    onAction: (SolicitationAction) -> Unit = {},
    tiposSanguineo: List<String>,
   requestDonation: RequestDonation,
    showDetails: Boolean
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (showDetails) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ModalBottomSheet(
                onDismissRequest = { onAction(SolicitationAction.OnDismiss) },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Tipos Sanguineos Compatíveis",
                        style = TextStyle(
                            color = Color(0xFF6A343A),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(end = 8.dp),
                        text = tiposSanguineo.joinToString(", "),
                        style = TextStyle(
                            color = Color(0xFF95313B),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Contato",
                        style = TextStyle(
                            color = Color(0xFF6A343A),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(end = 8.dp),
                        text = requestDonation.phone ?: "",
                        style = TextStyle(
                            color = Color(0xFF95313B),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Local da Internação",
                        style = TextStyle(
                            color = Color(0xFF6A343A),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(end = 8.dp),
                        text = requestDonation.local ?: "",
                        style = TextStyle(
                            color = Color(0xFF95313B),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            onAction(SolicitationAction.OnAccepted(requestDonation))
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
                            "Aceitar",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
        }
    }

}

@Composable
@Preview(showBackground = true)
fun RequestDonationDetailsBottomSheetPreview() {
    MaterialTheme {
        RequesetDonationDetailsBottomSheet(
            onAction = {},
            tiposSanguineo = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"),
            requestDonation = RequestDonation(
                id = "1",
                userId = "1",
                name = "John Doe",
                phone = "123456789",
                local = "Hospital A",
                state = "SP",
                city = "São Paulo",
                bloodType = "A+",
                status = "Pendente"
            ),
            showDetails = true
        )
    }
}