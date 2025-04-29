package br.com.akgs.doevida.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermosDeUsoBottomSheet(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (state.showTermos) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ModalBottomSheet(
                onDismissRequest = { onAction(ProfileAction.OnDismiss)},
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Termos de Uso e Política de Privacidade",
                        style = TextStyle(
                            color = Color(0xFF6A343A),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp),
                        color = Color(0xFF95313B)
                    )
                    Text(
                        modifier = Modifier
                            .padding(end = 8.dp),
                        text = "Bem-vindo ao DoeVida! Ao utilizar nosso aplicativo, você concorda com os seguintes\n \nTermos de Uso:",

                        style = TextStyle(
                            color = Color(0xFF95313B),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(end = 8.dp),
                        text = "\n" +
                                "1. Uso do Aplicativo: O DoeVida é destinado a conectar doadores e receptores de sangue. O uso indevido do aplicativo é estritamente proibido.\n" +
                                "2. Cadastro: Você deve fornecer informações precisas e atualizadas ao se cadastrar.\n" +
                                "3. Responsabilidade: O DoeVida não se responsabiliza por informações incorretas fornecidas pelos usuários.\n" +
                                "4. Alterações nos Termos: Reservamo-nos o direito de alterar estes Termos a qualquer momento. Notificaremos os usuários sobre mudanças significativas.\n" +
                                "\n" +
                                "Ao continuar utilizando o aplicativo, você concorda com os Termos atualizados.\n",
                        style = TextStyle(
                            color = Color(0xFF95313B),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(end = 8.dp),
                        text = "Política de Privacidade:",
                        style = TextStyle(
                            color = Color(0xFF95313B),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(end = 8.dp),
                        text = "\n" +
                                "1. Coleta de Dados: Coletamos informações pessoais para fornecer nossos serviços. Não compartilhamos seus dados com terceiros sem seu consentimento.\n" +
                                "2. Segurança: Tomamos medidas para proteger suas informações, mas não garantimos segurança absoluta.\n" +
                                "3. Alterações na Política: Reservamo-nos o direito de alterar esta Política a qualquer momento. Notificaremos os usuários sobre mudanças significativas.\n" +
                                "\n" +
                                "Ao utilizar o DoeVida, você concorda com nossa Política de Privacidade.",
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

}

@Composable
@Preview(showBackground = true)
fun TermosDeUsoBottomSheetPreview() {
    MaterialTheme {
        TermosDeUsoBottomSheet(
            onAction = {},
            state = ProfileState(
                showTermos = true,
            )

        )
    }
}