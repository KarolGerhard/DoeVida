package br.com.akgs.doevida.ui.solicitacion

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import br.com.akgs.doevida.ui.enums.SolicitationState

@Composable
fun SolicitationScreen(){

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
                    contentDescription = "Solicitações",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(24.dp),
                )
                Text(
                    text = "Solicitações",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                    ),
                    modifier = androidx.compose.ui.Modifier
                )

            }
        },
        bottomBar = {
            // BottomBar()
        },
    ) {paddingValues ->
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
                val items = listOf("Item 1", "Item 2", "Item 3")
                items(items.size) { index ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
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
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = "Pedro Silva",
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
                                        text = SolicitationState.URGENTE.name,
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
                                    text = "O-, A+",
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
    SolicitationScreen()
}