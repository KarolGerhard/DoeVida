package br.com.akgs.doevida.ui.donation

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.akgs.doevida.infra.remote.entities.RequestDonation
import br.com.akgs.doevida.ui.donation.components.RequesetDonationDetailsBottomSheet
import br.com.akgs.doevida.ui.navigation.TopAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun SolicitationScreen(
    onAction: (SolicitationAction) -> Unit = {}
) {

    val viewModel = koinViewModel<SolicitationViewModel>()
    val donationState by viewModel.donationState.collectAsState()

    val actions = viewModel.actions

    LaunchedEffect(Unit) {
        actions.collect { action ->
            onAction(action)
        }
    }

    var selectedSolicitation by remember {
        mutableStateOf<RequestDonation>(
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
        )
    }

    LaunchedEffect(donationState.solitacoes) {
        viewModel.onAction(SolicitationAction.OnLaunch)
    }

    Scaffold(
        topBar = {
            TopAppBar("Pedidos de doações") {
                viewModel.onAction(SolicitationAction.OnBackClick)
            }
        },
        bottomBar = {
            RequesetDonationDetailsBottomSheet(
                onAccepted = {
                    viewModel.onAction(SolicitationAction.OnAccepted(selectedSolicitation))
                },
                onDismiss = {
                    viewModel.onAction(SolicitationAction.OnDismiss)
                },

                tiposSanguineo = donationState.tiposSanguineosCompativeis,
                requestDonation = selectedSolicitation,
                showDetails = donationState.showDetails,
            )
        },
    ) { paddingValues ->
        Box(
            modifier = androidx.compose.ui.Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val items = viewModel.donationState.value.solitacoes
                itemsIndexed(items) { _, it ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedSolicitation = it
                                viewModel.onAction(SolicitationAction.OnSelectItem(it.bloodType))
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
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = it.name ?: "",
                                    style = TextStyle(
                                        color = Color(0xFF95313B),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                )
                                Spacer(modifier = Modifier.weight(0.5f))
                                Badge(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .height(24.dp)
                                        .weight(0.5f),
                                    containerColor = Color(0xFF95313B),
                                    contentColor = Color.White,
                                ) {
                                    Text(
                                        text = it.status,
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                        )
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(end = 8.dp),
                                    text = "Tipo Sanguíneo:",
                                    style = TextStyle(
                                        color = Color(0xFF95313B),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                )
                                Text(
                                    text = it.bloodType ?: "",
                                    style = TextStyle(
                                        color = Color(0xFF95313B),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun SolicitationScreenPreview() {
    SolicitationScreen(
    )
}