package br.com.akgs.doevida.ui.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType.Companion.PrimaryEditable
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.akgs.doevida.ui.util.PhoneMaskVisualTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterFirstPartForm(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
    onNext: () -> Unit
) {

    var selectEstado by remember { mutableStateOf("") }
    var isDropdownEstadoExpanded by remember { mutableStateOf(false) }
    val estados by remember { mutableStateOf(state.estados) }

    var selectCidade by remember { mutableStateOf("") }
    var isDropdownCidadeExpanded by remember { mutableStateOf(false) }
    val cidades = state.cidades

    var selectedBloodType by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    var birthDate by remember { mutableStateOf("") }


    val tipoSanguineo = listOf(
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
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = state.name,
            onValueChange = { onAction(RegisterAction.OnNameChange(it)) },
            label = { Text("Nome completo") },
            modifier = Modifier.fillMaxWidth(),
            keyboardActions = KeyboardActions(
                onDone = {
                    defaultKeyboardAction(ImeAction.Next)
                }
            ),
        )
        DateInputField(
            label = "Data de nascimento",
            selectedDate = state.dataNasc,
            onDateSelected = { onAction(RegisterAction.OnDataNascChange(it)) }
        )
        OutlinedTextField(
            value = state.phone,
            onValueChange = { onAction(RegisterAction.OnPhoneChange(it)) },
            label = { Text("Celular") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PhoneMaskVisualTransformation(),
            keyboardActions = KeyboardActions(
                onDone = {
                    defaultKeyboardAction(ImeAction.Next)
                }
            ),
        )
        Text(
            text = "Tipo sanguíneo",
            style = TextStyle(
                color = Color(0xFF95313B),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            ),
            modifier = Modifier.padding(start = 8.dp),
        )
        ExposedDropdownMenuBox(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp)),
            expanded = isDropdownExpanded,
            onExpandedChange = {
                isDropdownExpanded = !isDropdownExpanded
            }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    modifier = Modifier
                        .menuAnchor(type = PrimaryEditable, enabled = true)
                        .fillMaxWidth(),
                    value = selectedBloodType,
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = {
                        TrailingIcon(expanded = isDropdownExpanded)
                    },
                )
            }

            ExposedDropdownMenu(expanded = isDropdownExpanded,
                onDismissRequest = {
                    isDropdownExpanded = false
                }
            ) {
                tipoSanguineo.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(item)
                            }
                        },
                        onClick = {
                            selectedBloodType = tipoSanguineo[index]
                            isDropdownExpanded = false
                            onAction(RegisterAction.OnTipoSanguineoChange(tipoSanguineo[index]))
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
        Text(
            text = "Estado",
            style = TextStyle(
                color = Color(0xFF95313B),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            ),
            modifier = Modifier.padding(start = 8.dp)
        )

        ExposedDropdownMenuBox(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp)),
            expanded = isDropdownEstadoExpanded,
            onExpandedChange = {
                isDropdownEstadoExpanded = !isDropdownEstadoExpanded
            }
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    modifier = Modifier.menuAnchor(PrimaryEditable, true).fillMaxWidth(),
                    value = selectEstado,
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = {
                        TrailingIcon(expanded = isDropdownEstadoExpanded)
                    },
                )
            }

            ExposedDropdownMenu(expanded = isDropdownEstadoExpanded,
                onDismissRequest = {
                    isDropdownEstadoExpanded = false
                }
            ) {
                estados.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(item)
                            }
                        },
                        onClick = {
                            selectEstado = estados[index]
                            isDropdownEstadoExpanded = false
                            onAction(RegisterAction.OnEstadoChange(estados[index]))
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
        Text(
            text = "Cidade",
            style = TextStyle(
                color = Color(0xFF95313B),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            ),
            modifier = Modifier.padding(start = 8.dp)
        )
        ExposedDropdownMenuBox(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp)),
            expanded = isDropdownCidadeExpanded,
            onExpandedChange = {
                isDropdownCidadeExpanded = !isDropdownCidadeExpanded
            }
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    value = selectCidade,
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = {
                        TrailingIcon(expanded = isDropdownCidadeExpanded)
                    },
                )
            }

            ExposedDropdownMenu(expanded = isDropdownCidadeExpanded,
                onDismissRequest = {
                    isDropdownCidadeExpanded = false
                }
            ) {
                cidades.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(item)
                            }
                        },
                        onClick = {
                            selectCidade = cidades[index]
                            isDropdownCidadeExpanded = false
                            onAction(RegisterAction.OnCidadeChange(cidades[index]))
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
    ElevatedButton(
        onClick = onNext,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            Color(0xFF690714),
            contentColor = Color(0xFFFFFFFF)
        ),
    ) {
        Text("Próximo")
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
private fun RegisterFirstPartFormPreview() {
    RegisterFirstPartForm(
        state = RegisterState(),
        onAction = {},
        onNext = {}
    )
}