package br.com.akgs.doevida.ui.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(
//    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
) {
    val viewModel = koinViewModel<RegisterViewModel>()
    val state by viewModel.registerState.collectAsState()

    var currentStep by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Seja Bem Vindo,",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp),
            color = Color(0xFF95313B)
        )
        Text(
            text = "Por favor, informe os seguintes dados:",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp),
            color = Color(0xFF95313B)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            if (currentStep == 1) {
                // Primeira parte do formulário
                RegisterFirstPartForm(
                    state = state,
                    onAction = { action -> viewModel.onAction(action) }) {
                    currentStep = 2
                }
            } else {
                // Segunda parte do formulário
                RegisterSecondPartForm(
                    state = state,
                    onAction = { action -> viewModel.onAction(action) }) {
                    currentStep = 1
                }
            }
        }
    }
}

@Composable
fun RegisterSecondPartForm(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = state.email,
            onValueChange = { onAction(RegisterAction.OnEmailChange(it)) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.password,
            onValueChange = { onAction(RegisterAction.OnPasswordChange(it)) },
            label = { Text("Senha") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.passwordValid,
            onValueChange = { onAction(RegisterAction.OnConfirmPasswordChange(it)) },
            label = { Text("Repita a senha") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ElevatedButton(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF690714),
                    contentColor = Color(0xFFFFFFFF)
                ),
            ) {
                Text("Voltar")
            }
            ElevatedButton(
                onClick = { onAction(RegisterAction.OnRegisterClick) },
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF690714),
                    contentColor = Color(0xFFFFFFFF)
                ),
            ) {
                Text("Salvar")
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun RegisterScreenPreview() {
    RegisterScreen(
//        state = RegisterState(),
        onAction = {}
    )
}