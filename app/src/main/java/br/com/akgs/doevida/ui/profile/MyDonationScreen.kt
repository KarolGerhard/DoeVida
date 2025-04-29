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
import androidx.compose.material3.HorizontalDivider
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
import br.com.akgs.doevida.ui.navigation.TopAppBar
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyDonationScreen(
    onAction: (MyDonationAction)-> Unit = {},
){

    val viewModel = koinViewModel<MyDonationViewModel>()
    val state by viewModel.uiState.collectAsState()

    val actions = viewModel.actions

    LaunchedEffect(Unit) {
        actions.collect { action ->
            onAction(action)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar("Minhas doações") {
                viewModel.onAction(MyDonationAction.OnBackClick)
            }
        },
        bottomBar = {
            AddDonationBottomSheet(
                state = state,
                onAction = { viewModel.onAction(it) }
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Aceita",
                    style = TextStyle(
                        color = Color(0xFF95313B),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFDEE1)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color(0xFF95313B))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        InfoField(
                            label = "Nome paciente",
                            value = state.donation?.name ?: "-"
                        )
                        InfoField(
                            label = "Tipo Sanguineo",
                            value = state.donation?.bloodType ?: "-"
                        )
                        InfoField(
                            label = "Telefone",
                            value = state.donation?.phone ?: "-"
                        )
                        InfoField(
                            label = "Local Internação",
                            value = state.donation?.local ?: "-"
                        )

                    }
                }
                HorizontalDivider(modifier = Modifier.padding(8.dp))
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "Realizadas",
                    style = TextStyle(
                        color = Color(0xFF95313B),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                )
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    val items = state.donations
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
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(end = 8.dp),
                                    text = it.dateDonation,
                                    style = TextStyle(
                                        color = Color(0xFF95313B),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Local: ${it.localDonation}",
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
                    onClick = {
                        viewModel.onAction(MyDonationAction.OnShowAdd)
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
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
}

@Preview
@Composable
fun MyDonationScreenPreview() {
    MyDonationScreen(
        onAction = {}
    )
}