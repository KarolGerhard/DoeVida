package br.com.akgs.doevida.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import br.com.akgs.doevida.ui.register.RegisterAction
import br.com.akgs.doevida.ui.theme.DoeVidaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onAction: (HomeAction) -> Unit,) {
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        color = Color(0xFF95313B),
                        shape = RoundedCornerShape(0.dp, 0.dp, 54.dp, 54.dp)
                    ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Olá", style = TextStyle(color = Color.White, fontSize = 24.sp))
                        Text(
                            "Maria de Fátima",
                            style = TextStyle(color = Color.White, fontSize = 18.sp)
                        )
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.ic_notification),
                        contentDescription = "Notification",
                        modifier = Modifier.size(36.dp),
                        tint = Color.White
                    )
                }
            }
        },
        containerColor = Color(0x99FFFFFF),
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                            .background(
                                color = Color(0xFFFFDEE1),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Color(0xFF95313B),
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Tipo",
                                    style = TextStyle(
                                        color = Color(0xFF613338),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                )
                                Text(
                                    text = "sanguíneo",
                                    style = TextStyle(
                                        color = Color(0xFF613338),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                )
                                Text(
                                    text = "O-",
                                    modifier = Modifier.padding(top = 14.dp),
                                    style = TextStyle(
                                        color = Color(0xFFBE1E2D),
                                        fontSize = 32.sp,
                                    )
                                )
                            }
                            Icon(
                                painter = painterResource(id = R.drawable.ic_rh),
                                contentDescription = "Blood Donation",
                                modifier = Modifier.size(96.dp),
                                tint = Color(0x8C95313B)
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                            .background(
                                color = Color(0xFFFFDEE1),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Color(0xFF95313B),
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Total de",
                                    style = TextStyle(
                                        color = Color(0xFF613338),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                )
                                Text(
                                    text = "doações",
                                    style = TextStyle(
                                        color = Color(0xFF613338),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                )
                                Text(
                                    text = "3",
                                    modifier = Modifier.padding(top = 14.dp),
                                    style = TextStyle(
                                        color = Color(0xFFBE1E2D),
                                        fontSize = 32.sp,
                                    )
                                )
                            }
                            Icon(
                                painter = painterResource(id = R.drawable.ic_total),
                                contentDescription = "Blood Donation",
                                modifier = Modifier
                                    .size(96.dp)
                                    .padding(0.dp),
                                tint = Color(0x8C95313B)
                            )
                        }
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFDEE1)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color(0xFF95313B))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_calendar),
                            contentDescription = "Blood Donation",
                            modifier = Modifier.size(36.dp),
                            tint = Color(0x8C95313B)
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Próxima doação",
                                style = TextStyle(
                                    color = Color(0xFF613338),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            )
                            Text(
                                text = "Janeiro",
                                style = TextStyle(
                                    color = Color(0xFF95313B),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            )
                            Text(
                                text = "Você não está apto, aguarde o tempo correto.",
                                style = TextStyle(
                                    color = Color(0xFF613338),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            )
                        }


                    }
                }
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color(0x3395313B),
                    thickness = 1.dp
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                        .clickable { onAction(HomeAction.NavigateToSolicitation) },
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFDFDFD)
                    ),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .border(
                                width = 1.dp,
                                color = Color(0xFF95313B),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.ic_donation),
                            contentDescription = "Blood Donation",
                            modifier = Modifier.size(48.dp),

                            tint = Color(0xFF95313B)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Solicitar doação",
                            style = TextStyle(
                                color = Color(0xFF95313B),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                        )
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFDFDFD)
                    ),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .padding(start = 12.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_hospital),
                            contentDescription = "Blood Donation",
                            modifier = Modifier.size(36.dp),

                            tint = Color(0xFF95313B)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Encontrar Hemocentros",
                            style = TextStyle(
                                color = Color(0xFF95313B),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                        )
                    }
                }


            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    DoeVidaTheme {
        HomeScreen(onAction = {})
    }

}
