package br.com.akgs.doevida.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.akgs.doevida.R
import br.com.akgs.doevida.infra.remote.entities.Donation
import br.com.akgs.doevida.infra.remote.entities.RequestDonation
import br.com.akgs.doevida.ui.donation.components.RequesetDonationDetailsBottomSheet
import br.com.akgs.doevida.ui.enums.SolicitationState
import br.com.akgs.doevida.ui.home.HomeAction
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyDonationScreen(){
    val viewModel = koinViewModel<ProfileViewModel>()

//    val donationState by viewModel.donationState.collectAsState()
    var selectedSolicitation by remember { mutableStateOf<RequestDonation>(
        RequestDonation(
            id = "",
            userId = "",
            name = "",
            phone = "",
            local = "",
            state = "",
            city = "",
            bloodType = "",
            status = ""
        )
    ) }

//    LaunchedEffect(donationState.solitacoes) {
//        viewModel.onAction(SolicitationAction.OnLaunch)
//    }

    Scaffold(
        topBar = {
            NavigationBar(
                containerColor = Color(0xFF95313B),
                modifier = androidx.compose.ui.Modifier
                    .fillMaxWidth()
                    .clickable { /* Handle click */ }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Pedidos de doações",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(20.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Pedidos de doações",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                    ),
                )

            }
        },
        bottomBar = {
            AddDonationBottomSheet(
                showAdd = false,
            )
        },
    ) {paddingValues ->
        Column (
            modifier = androidx.compose.ui.Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                modifier = Modifier
                    .padding(16.dp),
                text = "Realizadas",
                style = TextStyle(
                    color = Color(0xFF95313B),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            )
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val items = listOf<Donation>(
                    Donation(
                        userId = "",
                        donationAccepted = RequestDonation(
                            id = "",
                            userId = "",
                            name = "",
                            phone = "",
                            local = "",
                            state = "",
                            city = "",
                            bloodType = "",
                            status = ""
                        )
                    ),
                    Donation(
                        userId = "",
                        donationAccepted = RequestDonation(
                            id = "",
                            userId = "",
                            name = "",
                            phone = "",
                            local = "",
                            state = "",
                            city = "",
                            bloodType = "",
                            status = ""
                        )
                    ),
                )
                itemsIndexed(items) { _, it ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                            },
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFCEEEF)
                        ),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Column (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(end = 8.dp),
                                text = "11/01/2025",
                                style = TextStyle(
                                    color = Color(0xFF95313B),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Local: opop",
//                                text = "Local: ${it.local}",
                                style = TextStyle(
                                    color = Color(0xFF95313B),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                )
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    Color(0xFFFFDEE1),
                    contentColor = Color(0xFF000000)
                ),
                border = BorderStroke(1.dp, Color(0xFF95313B))
            ) {

                Text(
                    text = "Registrar doação",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun MyDonationScreenPreview() {
    MyDonationScreen(
    )
}