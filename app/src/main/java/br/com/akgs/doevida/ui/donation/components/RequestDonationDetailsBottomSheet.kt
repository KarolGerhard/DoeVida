package br.com.akgs.doevida.ui.donation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.akgs.doevida.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequesetDonationDetailsBottomSheet(

) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val options = listOf(
        "A+",
        "A-",
        "B+",
        "B-",
        "AB+",
        "AB-",
        "O+",
        "O-",
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ModalBottomSheet(
            onDismissRequest = { /*TODO*/ },
            sheetState = sheetState
        ) {
            Text(modifier = Modifier.padding(8.dp), text = "Detalhes")
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_person),
                        contentDescription = "Blood Donation",
                        modifier = Modifier.size(18.dp),
                        tint = Color(0xFF95313B)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Pedro silva")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_rh),
                        contentDescription = "Blood Donation",
                        modifier = Modifier.size(18.dp),
                        tint = Color(0xFF95313B)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("O-")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_phone),
                        contentDescription = "Blood Donation",
                        modifier = Modifier.size(18.dp),
                        tint = Color(0xFF95313B)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("65993164011")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_hospital),
                        contentDescription = "Blood Donation",
                        modifier = Modifier.size(18.dp),
                        tint = Color(0xFF95313B)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Hospital Geral, UTI")
                }


                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Aceitar")
                }
            }
        }

    }
}

@Composable
@Preview(showBackground = true)
fun RequestDonationDetailsBottomSheetPreview() {
    MaterialTheme {
        RequesetDonationDetailsBottomSheet()
    }
}