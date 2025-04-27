package br.com.akgs.doevida.ui.donation.requestDonation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.collectAsState
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
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestDonationScreen(
    onAction: (RequestDonationAction) -> Unit = {},
) {

    val viewModel = koinViewModel<RequestDonationViewModel>()
    val state by viewModel.requestDonationState.collectAsState()
    val onAction = viewModel::onAction
    val scrollState = rememberScrollState()


    var selectEstado by remember { mutableStateOf("") }
    var isDropdownEstadoExpanded by remember { mutableStateOf(false) }
    val estados by remember { mutableStateOf(state.estados) }

    var selectCidade by remember { mutableStateOf("") }
    var isDropdownCidadeExpanded by remember { mutableStateOf(false) }
    val cidades = state.cidades

    var selectedBloodType by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    var selectedTipoPedido by remember { mutableStateOf("") }
    var isDropdownTipoPedidoExpanded by remember { mutableStateOf(false) }
    val tipos = listOf(
        "Urgente",
        "Reposição"
    )


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
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Preencha as informações do paciente",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp),
            color = Color(0xFF95313B)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = state.name,
                onValueChange = { onAction(RequestDonationAction.OnNameChange(it)) },
                label = { Text("Nome completo") },
                modifier = Modifier.fillMaxWidth(),
                keyboardActions = KeyboardActions(
                    onDone = {
                        defaultKeyboardAction(ImeAction.Next)
                    }
                ),
            )
            OutlinedTextField(
                value = state.phone,
                onValueChange = {
                    onAction(RequestDonationAction.OnPhoneChange(it)) },
                label = { Text("Celular") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PhoneMaskVisualTransformation(),
                keyboardActions = KeyboardActions(
                    onDone = {
                        defaultKeyboardAction(ImeAction.Next)
                    }
                ),
            )
            OutlinedTextField(
                value = state.local,
                onValueChange = { onAction(RequestDonationAction.OnLocalChange(it)) },
                label = { Text("Local internação") },
                modifier = Modifier.fillMaxWidth(),
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
                    .width(200.dp)
                    .clip(RoundedCornerShape(10.dp)),
                expanded = isDropdownExpanded,
                onExpandedChange = {
                    isDropdownExpanded = !isDropdownExpanded
                }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically
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
                                onAction(RequestDonationAction.OnTipoSanguineoChange(tipoSanguineo[index]))
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
            Text(
                text = "Tipo pedido",
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
                    .width(200.dp)
                    .clip(RoundedCornerShape(10.dp)),
                expanded = isDropdownTipoPedidoExpanded,
                onExpandedChange = {
                    isDropdownTipoPedidoExpanded = !isDropdownTipoPedidoExpanded
                }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    TextField(
                        modifier = Modifier
                            .menuAnchor(type = PrimaryEditable, enabled = true)
                            .fillMaxWidth(),
                        value = selectedTipoPedido,
                        onValueChange = { },
                        readOnly = true,
                        trailingIcon = {
                            TrailingIcon(expanded = isDropdownTipoPedidoExpanded)
                        },
                    )
                }

                ExposedDropdownMenu(expanded = isDropdownTipoPedidoExpanded,
                    onDismissRequest = {
                        isDropdownTipoPedidoExpanded = false
                    }
                ) {
                    tipos.forEachIndexed { index, item ->
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
                                selectedTipoPedido = tipos[index]
                                isDropdownTipoPedidoExpanded = false
                                onAction(RequestDonationAction.OnTipoPedidoChange(tipos[index]))
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
                    .width(200.dp)
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
                        modifier = Modifier.menuAnchor(PrimaryEditable, true),
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
                                onAction(RequestDonationAction.OnEstadoChange(estados[index]))
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
                    .width(200.dp)
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
                        modifier = Modifier.menuAnchor(),
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
                                onAction(RequestDonationAction.OnCidadeChange(cidades[index]))
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
            ElevatedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                onClick = { onAction(RequestDonationAction.OnSaveClick) },
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
fun RequestDonationScreenPreview() {
    RequestDonationScreen()
}