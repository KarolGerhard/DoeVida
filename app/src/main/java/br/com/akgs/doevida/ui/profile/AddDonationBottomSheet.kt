package br.com.akgs.doevida.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.akgs.doevida.ui.util.DateVisualTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDonationBottomSheet(
    state: MyDonationState,
    onAction: (MyDonationAction) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (state.showAddDonation) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ModalBottomSheet(
                onDismissRequest = { onAction(MyDonationAction.OnDismiss) },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = state.date,
                        onValueChange = { newValue ->
                            if (newValue.length <= 8 && newValue.all { it.isDigit() }) {
                                onAction(MyDonationAction.OnDateChange(newValue))
                            }
                        },
                        label = { Text("Data da coleta") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = DateVisualTransformation(),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                defaultKeyboardAction(ImeAction.Next)
                            }
                        ),
                        placeholder = { Text("DD/MM/AAAA") },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = state.local,
                        onValueChange = {
                            onAction(MyDonationAction.OnLocalChange(it))
                        },
                        label = { Text("Local da doação") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                defaultKeyboardAction(ImeAction.Next)
                            }
                        ),
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            onAction(MyDonationAction.OnSaveDonation)
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
                            "Salvar",
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
fun AddDonationBottomSheetPreview() {
    MaterialTheme {
        AddDonationBottomSheet(
            onAction = {},
            state = MyDonationState(
                showAddDonation = true,
                local = "",
                date = "",
                isLoading = false,
                error = null
            )
        )
    }
}